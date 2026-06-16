package com.sparta_order.order.adapter.api;

import com.sparta_order.order.application.service.OrderSummary;

public record OrderResponse(Long orderId, String productName, Integer quantity, Long totalPrice) {
	public static OrderResponse of(OrderSummary order) {
		return new OrderResponse(
				order.getOrderId(),
				order.getProductName(),
				order.getQuantity(),
				order.getTotalPrice()
		);
	}
}
