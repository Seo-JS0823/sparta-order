package com.sparta_order.product.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductStatus {
	// 판매중
	SELLING("판매중"),
	// 어떠한 이유로 판매중지
	DISCOUNTINUED("판매 중지"),
	
	;
	private final String info;
}
