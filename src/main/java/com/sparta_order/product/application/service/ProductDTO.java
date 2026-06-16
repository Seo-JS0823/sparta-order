package com.sparta_order.product.application.service;

import com.sparta_order.product.domain.Product;
import com.sparta_order.product.domain.ProductStatus;

public record ProductDTO(Long productId, String name, Long price, Integer stock, ProductStatus status) {

	public static ProductDTO of(Product product) {
		return new ProductDTO(
				product.getId(),
				product.getName(),
				product.getPrice(),
				product.getStock(),
				product.getStatus()
		);
	}
	
}
