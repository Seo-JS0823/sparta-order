package com.sparta_order.order.adapter.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta_order._global.response.ApiResponse;
import com.sparta_order._global.response.ResponseCode;
import com.sparta_order.order.application.service.OrderCommandService;
import com.sparta_order.order.application.service.OrderSummary;
import com.sparta_order.order.domain.request.OrderCreateRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/order/c")
@RequiredArgsConstructor
public class OrderCommandAPI {

	private final OrderCommandService orderCommand;
	
	// 주문 하기
	@PostMapping("")
	public ApiResponse<OrderResponse> orderCreate(@RequestBody OrderCreateRequest orderCreate) {
		OrderSummary order = orderCommand.createOrder(orderCreate);
		
		OrderResponse data = OrderResponse.of(order);
		
		return ApiResponse.response(ResponseCode.CREATED, data);
	}
	
	
}
