package com.sparta_order.order.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta_order.order.application.repository.OrderRepository;
import com.sparta_order.order.domain.Order;
import com.sparta_order.order.domain.request.OrderCancelRequest;
import com.sparta_order.order.domain.request.OrderCreateRequest;
import com.sparta_order.product.application.port.in.ProductFinder;
import com.sparta_order.product.application.service.ProductCommandService;
import com.sparta_order.product.domain.Product;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class OrderCommandService {

	private final OrderRepository orderRepo;
	
	private final ProductFinder productFinder;
	
	private final ProductCommandService productCommand;
	
//	// 1. 주문 등록
//	public OrderSummary createOrder(OrderCreateRequest orderCreate) {
//		Product product = productFinder.getProductById(orderCreate.productId());
//		
//		Order order = Order.create(product, orderCreate.quantity());
//		
//		Order entity = orderRepo.save(order);
//		
//		product.orderStockDecrease(entity.getQuantity());
//		
//		return OrderSummary.of(entity);
//	}
	
	// 1. 주문 등록 Lost Update 해결 Row Lock
	public OrderSummary createOrder(OrderCreateRequest orderCreate) {
		Product product = productFinder.getProductById(orderCreate.productId());
		
		// updateCount == 0 : 실패
		// updateCount == 1 : 성공
		int updateCount = productCommand.decreaseStock(product.getId(), orderCreate.quantity());
		
		if(updateCount == 0) {
			throw new IllegalStateException("재고가 부족하여 주문이 취소되었습니다.");
		}
		
		Order order = Order.create(product, orderCreate.quantity());
		
		Order entity = orderRepo.save(order);
		
		return OrderSummary.of(entity);
	}
	
	// 2. 주문 취소
	public OrderSummary cancelOrder(OrderCancelRequest orderCancel) {
		Order order = orderRepo.findById(orderCancel.orderId())
				.orElseThrow(() -> new IllegalArgumentException("해당 주문건이 존재하지 않습니다."));
		
		order.orderCancel(orderCancel.reason());
		
		Order entity = orderRepo.save(order);
		
		return OrderSummary.of(entity);
	}
	
}
