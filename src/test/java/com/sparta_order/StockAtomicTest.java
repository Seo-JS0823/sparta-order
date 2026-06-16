package com.sparta_order;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.sparta_order.order.application.repository.OrderRepository;
import com.sparta_order.order.application.service.OrderCommandService;
import com.sparta_order.order.domain.request.OrderCreateRequest;
import com.sparta_order.product.application.repository.ProductRepository;
import com.sparta_order.product.application.service.ProductCommandService;
import com.sparta_order.product.domain.Product;
import com.sparta_order.product.domain.request.ProductRequest.ProductCreateRequest;

@SpringBootTest
public class StockAtomicTest {

	@Autowired
	OrderCommandService orderService;
	
	@Autowired
	ProductCommandService productCommand;
	
	@Autowired
	ProductRepository productRepo;
	
	@Autowired
	OrderRepository orderRepo;
	
	Product product;
	
	@BeforeEach
	void setUp() {
		ProductCreateRequest productCreate = new ProductCreateRequest(
				"오징어",
				5000L,
				30
		);
		
		product = productCommand.create(productCreate);
	}
	
	@Test
	@DisplayName("재고 원자적 감소")
	void stockAtomic() throws Exception {
		int threadCount = 31;
		
		int orderQuantity = 1;
		
		ExecutorService executor = Executors.newFixedThreadPool(threadCount);
		
		CountDownLatch ready = new CountDownLatch(threadCount);
		CountDownLatch start = new CountDownLatch(1);
		CountDownLatch doneLatch = new CountDownLatch(threadCount);
		
		AtomicInteger successCount = new AtomicInteger();
		AtomicInteger failCount = new AtomicInteger();
		
		List<Throwable> exceptions = Collections.synchronizedList(new ArrayList<>());
		
		for (int i = 0; i < threadCount; i++) {
			executor.submit(() -> {
				try {
					
					ready.countDown();
					start.await();
					
					orderService.createOrder(new OrderCreateRequest(product.getId(), orderQuantity));
					
					successCount.incrementAndGet();
				} catch (Exception e) {
					exceptions.add(e);
					
					if(e instanceof IllegalStateException) {
						failCount.incrementAndGet();
					}
					
				} finally {
					doneLatch.countDown();
				}
			});
		}
		
		ready.await();
		
		start.countDown();
		
		doneLatch.await();
		
		executor.shutdown();
		
		
		
		
		Product result = productRepo.findById(product.getId()).orElseThrow();
		
		assertThat(result.getStock()).isEqualTo(0);
		assertThat(successCount.get()).isEqualTo(30);
		assertThat(failCount.get()).isEqualTo(1);
		assertThat(orderRepo.count()).isEqualTo(30);
		
		assertThat(exceptions).hasSize(1);
		assertThat(exceptions.get(0)).isInstanceOf(IllegalStateException.class);
		assertThat(exceptions.get(0)).hasMessage("재고가 부족하여 주문이 취소되었습니다.");
		
	}
	
}
