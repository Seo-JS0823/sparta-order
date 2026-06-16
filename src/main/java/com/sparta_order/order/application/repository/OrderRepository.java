package com.sparta_order.order.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta_order.order.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
