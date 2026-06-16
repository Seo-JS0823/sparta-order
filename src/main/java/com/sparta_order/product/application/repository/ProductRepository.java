package com.sparta_order.product.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.sparta_order.product.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findByNameContainingIgnoreCase(String name);
	
	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("""
			UPDATE Product p
			SET p.stock = p.stock - :quantity
			WHERE
					p.id = :productId
					AND p.stock >= :quantity
	""")
	int decreaseStock(Long productId, Integer quantity);

}
