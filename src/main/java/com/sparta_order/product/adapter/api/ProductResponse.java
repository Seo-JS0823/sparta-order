package com.sparta_order.product.adapter.api;

import com.sparta_order.product.domain.Product;

public record ProductResponse(Long productId, String name, Long price, Integer stock, String state, String message) {
	public static ProductResponse of(Product product, String message) {
		return new ProductResponse(
				product.getId(),
				product.getName(),
				product.getPrice(),
				product.getStock(),
				product.getStatus().getInfo(),
				message
		);
	}
}
