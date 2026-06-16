package com.sparta_order.product.domain.request;

public final class ProductRequest {

	public record ProductCreateRequest(String name, Long price, Integer stock) {}
	
	public record ProductUpdateNameRequest(String name) {}
	
	public record ProductUpdatePriceRequest(Long price) {}
	
	public record ProductUpdateStockRequest(Integer stock) {}
}
