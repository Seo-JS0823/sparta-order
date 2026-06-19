package com.sparta_order.order.application.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta_order.order.application.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class OrderFindService {

	private final OrderRepository orderRepo;
	
	public OrderSummary findOrder(Long orderId) {
		log.info("===== Order Query Start =====");
		OrderSummary order = orderRepo.findByOrderId(orderId);
		log.info("===== Order Query End =====");
		
		return order;
	}
	
}
