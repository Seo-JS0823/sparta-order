package com.sparta_order.order.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sparta_order.order.application.service.OrderSummary;
import com.sparta_order.order.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	@Query("""
			SELECT new com.sparta_order.order.application.service.OrderSummary(o.id, p.name, o.quantity, o.totalPrice, o.status)
			FROM Order o
			JOIN o.product p
			WHERE o.id = :orderId
	""")
	OrderSummary findByOrderId(Long orderId);
}
