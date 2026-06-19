package com.sparta_order.order.adapter.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta_order._global.response.ApiResponse;
import com.sparta_order._global.response.ResponseCode;
import com.sparta_order.order.application.service.OrderFindService;
import com.sparta_order.order.application.service.OrderSummary;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/order/r")
@RequiredArgsConstructor
public class OrderReadAPI {
	
	private final OrderFindService orderFinder;
	
	@GetMapping("/{orderId}")
	public ApiResponse<OrderSummary> getOrder(@PathVariable Long orderId) {
		OrderSummary order = orderFinder.findOrder(orderId);
		
		return ApiResponse.response(ResponseCode.SUCCESS, order);
	}
}
