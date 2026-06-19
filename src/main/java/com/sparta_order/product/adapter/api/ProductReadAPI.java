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
import com.sparta_order.product.domain.Product;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/product/r")
@RequiredArgsConstructor
public class ProductReadAPI {

	private final ProductFindService productFinder;
	
	@GetMapping("/{productId}")
	public ApiResponse<ProductResponse> productOneRead(@PathVariable Long productId) {
		Product product = productFinder.getProductByIdNotDelete(productId);
		
		ProductResponse data = ProductResponse.of(product, "");
		
		return ApiResponse.response(ResponseCode.SUCCESS, data);
	}
	
	@GetMapping("")
	public ApiResponse<List<ProductResponse>> productOneNameRead(@RequestParam String name) {
		List<Product> productList = productFinder.getProductListByNameNotDelete(name);
		
		List<ProductResponse> data = productList.stream()
				.map(pro -> {
					return ProductResponse.of(pro, null);
				})
				.toList();
		
		return ApiResponse.response(ResponseCode.SUCCESS, data);
	}
	
}
