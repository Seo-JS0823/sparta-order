package com.sparta_order.product.application.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta_order.product.application.port.in.ProductFinder;
import com.sparta_order.product.application.repository.ProductRepository;
import com.sparta_order.product.domain.Product;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductFindService implements ProductFinder {

	private final ProductRepository productRepo;
	
	@Override
	public ProductDTO getProductDtoById(Long productId) {
		Product product = getProductById(productId);
		
		return ProductDTO.of(product);
	}
	
	@Override
	public Product getProductById(Long productId) {
		return productRepo.findById(productId)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
	}
	
	public Optional<Product> getProductByName(String name) {
		return productRepo.findByName(name);
	}
	
}
