package com.sparta_order.order.domain;

import java.time.Instant;

import com.sparta_order.product.domain.Product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 01. Field
 * 
 * product    : 주문한 상품
 * quantity   : 주문 수량
 * totalPrice : 주문 금액
 * status     : 주문 상태 (ORDERED, CANCELED)
 * createdAt  : 주문 생성 일시
 * ------------------------------
 * 
 * 02. Method
 * 
 * 
 */
@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;
	
	@Column(name = "quantity", nullable = false)
	private Integer quantity;
	
	@Column(name = "total_price", nullable = false)
	private Long totalPrice;
	
	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant createdAt;
	
	@Column(name = "cancel_reason")
	private String cancelReason;
	
	@Column(name = "cancled_at")
	private Instant canceldAt;
	
	
	public static Order create(Product product, Integer quantity) {
		Order order = new Order();
		
		order.validateQuantity(quantity);
		
		order.product = product;
		order.quantity = quantity;
		order.totalPrice = quantity * product.getPrice();
		
		order.status = OrderStatus.ORDERED;
		order.createdAt = Instant.now();
		
		order.cancelReason = null;
		order.canceldAt = null;
		
		return order;
	}
	
	public String getProductName() {
		return this.product.getName();
	}
	
	public void orderCancel(String cancelReason) {
		this.cancelReason = cancelReason;
		this.canceldAt = Instant.now();
		this.status = OrderStatus.CANCELED;
	}
	
	public void validateQuantity(Integer quantity) {
		if(quantity <= 0) {
			throw new IllegalArgumentException("주문 수량은 1개 이상이어야 합니다.");
		}
	}
	
}
