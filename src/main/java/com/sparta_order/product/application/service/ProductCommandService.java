package com.sparta_order.product.application.service;

import java.time.Instant;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta_order.product.application.repository.ProductRepository;
import com.sparta_order.product.domain.Product;
import com.sparta_order.product.domain.request.ProductRequest.ProductCreateRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class ProductCommandService {

	private final ProductRepository productRepo;
	
	private final ProductFindService productFinder;
	
	// 1. 상품 등록
	public Product create(String name, Long price, Integer stock) {
		Product product = Product.create(name, price, stock);
		
		return productRepo.save(product);
	}
	
	public Product create(ProductCreateRequest productCreate) {
		Product product = Product.create(productCreate);
		
		return productRepo.save(product);
	}
	
	// 2. 상품 이름 수정
	public Product changeName(Long productId, String newName) {
		Product product = productFinder.getProductById(productId);
		
		product.changeName(newName);
		
		return productRepo.save(product);
	}
	
	// 3. 상품 가격 수정
	public Product changePrice(Long productId, Long price) {
		Product product = productFinder.getProductById(productId);
		
		product.changePrice(price);
		
		return productRepo.save(product);
	}
	
	// 4. 상품의 재고 설정
	public Product changeStock(Long productId, Integer stock) {
		Product product = productFinder.getProductById(productId);
		
		product.changeStock(stock);
		
		return productRepo.save(product);
	}
	
	// 5. 상품 판매 중지 처리
	public Product discountinue(Long productId) {
		Product product = productFinder.getProductById(productId);
		
		product.discountinue();
		
		return productRepo.save(product);
	}
	
	// 6. 상품 판매 개시
	public Product selling(Long productId) {
		Product product = productFinder.getProductById(productId);
		
		product.selling();
		
		return productRepo.save(product);
	}
	
	// 7. 상품 삭제
	public Product delete(Long productId) {
		Product product = productFinder.getProductById(productId);
		
		Instant deletedAt = Instant.now();
		
		product.delete(deletedAt);
		
		return productRepo.save(product);
	}
	
	public int decreaseStock(Long productId, Integer quantity) {
		return productRepo.decreaseStock(productId, quantity);
	}
	
}
