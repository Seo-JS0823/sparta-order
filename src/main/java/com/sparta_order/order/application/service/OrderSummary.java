package com.sparta_order.order.application.service;

import com.sparta_order.order.domain.Order;
import com.sparta_order.order.domain.OrderStatus;

import lombok.Getter;

@Getter
public class OrderSummary {
	
	private final Long orderId;
	
	private final String productName;
	
	private final Integer quantity;
	
	private final Long totalPrice;
	
	private final String status;
	
	public OrderSummary(Long orderId, String productName, Integer quantity, Long totalPrice, OrderStatus status) {
		this.orderId = orderId;
		this.productName = productName;
		this.quantity = quantity;
		this.totalPrice = totalPrice;
		this.status = status.getInfo();
	}
	
	public static OrderSummary of(Order order) {
		OrderSummary summary = new OrderSummary(
				order.getId(),
				order.getProductName(),
				order.getQuantity(),
				order.getTotalPrice(),
				order.getStatus()
		);
		
		return summary;
	}
}
