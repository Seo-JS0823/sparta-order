package com.sparta_order.product.adapter.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta_order.product.application.service.ProductFindService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/product/r")
@RequiredArgsConstructor
public class ProductReadAPI {

	private final ProductFindService productFinder;
	
	
	
}
