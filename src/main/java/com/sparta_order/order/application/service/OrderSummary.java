package com.sparta_order.order.application.service;

import com.sparta_order.order.domain.Order;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderSummary {
	
	private final Long orderId;
	
	private final String productName;
	
	private final Integer quantity;
	
	private final Long totalPrice;
	
	private final String status;
	
	public static OrderSummary of(Order order) {
		OrderSummary summary = new OrderSummary(
				order.getId(),
				order.getProductName(),
				order.getQuantity(),
				order.getTotalPrice(),
				order.getStatus().getInfo()
		);
		
		return summary;
	}
}
