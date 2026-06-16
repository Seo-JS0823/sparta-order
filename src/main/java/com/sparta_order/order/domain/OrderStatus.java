package com.sparta_order.order.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
	// 주문 완료
	ORDERED("주문 완료"),
	
	// 주문 취소
	CANCELED("주문 취소"),
	
	;
	
	private final String info;
}
