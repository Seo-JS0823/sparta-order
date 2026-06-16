package com.sparta_order.product.adapter.api;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta_order._global.response.ApiResponse;
import com.sparta_order._global.response.ResponseCode;
import com.sparta_order.product.application.service.ProductCommandService;
import com.sparta_order.product.application.service.ProductDTO;
import com.sparta_order.product.application.service.ProductFindService;
import com.sparta_order.product.domain.Product;
import com.sparta_order.product.domain.request.ProductRequest.ProductCreateRequest;
import com.sparta_order.product.domain.request.ProductRequest.ProductUpdateNameRequest;
import com.sparta_order.product.domain.request.ProductRequest.ProductUpdatePriceRequest;
import com.sparta_order.product.domain.request.ProductRequest.ProductUpdateStockRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/product/c")
@RequiredArgsConstructor
public class ProductCommandAPI {
	private final ProductCommandService productCommand;
	
	private final ProductFindService productFinder;
	
	// 상품 등록
	@PostMapping("")
	public ApiResponse<ProductResponse> productCreate(@RequestBody ProductCreateRequest productCreate) {
		Product product = productCommand.create(productCreate);
		
		ProductResponse data = ProductResponse.of(
				product,
				product.getName() + " 상품이 성공적으로 등록되었습니다."
		);
		
		return ApiResponse.response(ResponseCode.CREATED, data);
	}
	
	// 상품 이름 수정
	@PatchMapping("/{productId}/name")
	public ApiResponse<ProductResponse> productNameUpdate(
			@PathVariable Long productId,
			@RequestBody ProductUpdateNameRequest productUpdateName) {
		
		ProductDTO before = productFinder.getProductDtoById(productId);
		
		Product product = productCommand.changeName(productId, productUpdateName.name());
		
		ProductResponse data = ProductResponse.of(
				product,
				before.name() + " 상품의 이름이 " + product.getName() + " 으로 수정되었습니다."
		);
		
		return ApiResponse.response(ResponseCode.UPDATED, data);
	}
	
	// 상품 가격 수정
	@PatchMapping("/{productId}/price")
	public ApiResponse<ProductResponse> productPriceUpdate(
			@PathVariable Long productId,
			@RequestBody ProductUpdatePriceRequest productUpdatePrice) {
		
		ProductDTO before = productFinder.getProductDtoById(productId);
		
		Product product = productCommand.changePrice(productId, productUpdatePrice.price());
		
		ProductResponse data = ProductResponse.of(
				product,
				before.name() + " 상품의 가격이 " + before.price() + " 에서 " + product.getPrice() + " 으로 수정되었습니다."
		);
		
		return ApiResponse.response(ResponseCode.UPDATED, data);
	}
	
	// 상품 재고 설정
	@PatchMapping("/{productId}/stock")
	public ApiResponse<ProductResponse> productStockUpdate(
			@PathVariable Long productId,
			@RequestBody ProductUpdateStockRequest productUpdateStock) {
		
		ProductDTO before = productFinder.getProductDtoById(productId);
		
		Product product = productCommand.changeStock(productId, productUpdateStock.stock());
		
		ProductResponse data = ProductResponse.of(
				product,
				before.name() + " 상품의 재고가 " + before.stock() + "개 에서 " + product.getStock() + "개로 수정되었습니다."
		);
		
		return ApiResponse.response(ResponseCode.UPDATED, data);
	}
	
	// 상품 판매 중지 처리
	@PatchMapping("/{productId}/discountinue")
	public ApiResponse<ProductResponse> productDiscountinue(@PathVariable Long productId) {
		
		Product product = productCommand.discountinue(productId);
		
		ProductResponse data = ProductResponse.of(product, "해당 상품이 판매 중지 처리되었습니다.");
		
		return ApiResponse.response(ResponseCode.UPDATED, data);
	}
	
	// 상품 판매 개시
	@PatchMapping("/{productId}/selling")
	public ApiResponse<ProductResponse> productSelling(@PathVariable Long productId) {
		
		Product product = productCommand.selling(productId);
		
		ProductResponse data = ProductResponse.of(product, "해당 상품이 성공적으로 개시되었습니다.");
		
		return ApiResponse.response(ResponseCode.UPDATED, data);
	}
	
	// 상품 삭제
	@DeleteMapping("/{productId}")
	public ApiResponse<ProductResponse> productDelete(@PathVariable Long productId) {
		
		Product product = productCommand.delete(productId);
		
		ProductResponse data = ProductResponse.of(product, "해당 상품이 삭제되었습니다.");
		 
		return ApiResponse.response(ResponseCode.DELETED, data);
	}
	
}
