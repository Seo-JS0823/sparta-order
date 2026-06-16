package com.sparta_order.product.adapter.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sparta_order._global.response.ApiResponse;
import com.sparta_order._global.response.ResponseCode;
import com.sparta_order.product.application.service.ProductDTO;
import com.sparta_order.product.application.service.ProductFindService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/product/r")
@RequiredArgsConstructor
public class ProductReadAPI {

	private final ProductFindService productFinder;
	
	@GetMapping("/{productId}")
	public ApiResponse<ProductDTO> productOneRead(@PathVariable Long productId) {
		ProductDTO product = productFinder.getProductDtoById(productId);
		
		return ApiResponse.response(ResponseCode.SUCCESS, product);
	}
	
	@GetMapping("")
	public ApiResponse<List<ProductDTO>> productOneNameRead(@RequestParam String name) {
		List<ProductDTO> productList = productFinder.getProductListByName(name);
		
		return ApiResponse.response(ResponseCode.SUCCESS, productList);
	}
	
}
