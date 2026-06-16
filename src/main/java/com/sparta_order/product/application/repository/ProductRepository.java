package com.sparta_order.product.application.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta_order.product.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	Optional<Product> findByName(String name);
	
	

}
