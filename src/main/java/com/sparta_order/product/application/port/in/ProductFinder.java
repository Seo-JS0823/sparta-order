package com.sparta_order.product.application.port.in;

import com.sparta_order.product.application.service.ProductDTO;
import com.sparta_order.product.domain.Product;

public interface ProductFinder {
	ProductDTO getProductDtoById(Long productId);
	
	Product getProductById(Long productId);
}
