package com.sparta_order.product.domain;

import java.time.Instant;

import com.sparta_order.product.domain.request.ProductRequest.ProductCreateRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 01. Field
 * 
 * name      : 상품의 이름
 * price     : 상품의 가격
 * stock     : 상품의 재고
 * status    : 상품 운영 상태
 * deletedAt : 상품 삭제 일시
 * ------------------------------
 * 
 * 02. Method
 * 
 * changeName    : 상품 이름 수정
 * changePrice   : 상품 가격 수정
 * changeStock   : 상품 재고 설정
 * discountinue : 상품 판매 중지 처리
 * selling       : 상품 판매 개시
 * delete        : 상품 삭제
 * 
 * isOrderable   : 주문이 가능한 상태인가?
 * isDeleted     : 상품이 삭제된 상태인가?
 * 
 * ------------------------------
 * 
 * 03. domain rule
 * 
 * 1. 상품명은 비어있을 수 없다.
 * 2. 가격(Price)은 0보다 커야한다.
 * 3. 재고(stock)는 마이너스로 내려갈 수 없고, 0 이면 주문 불가능한(품절) 상태다.
 * 4. 삭제된 상품은 상품 정보에 대하여 이름 수정, 가격 수정, 재고 설정을 할 수 없다.
 */

@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "price", nullable = false)
	private Long price;
	
	@Column(name = "stock", nullable = false)
	private Integer stock;
	
	@Column(name = "status", nullable = false)
	@Enumerated(EnumType.STRING)
	private ProductStatus status;
	
	@Column(name = "deleted_at")
	private Instant deletedAt;
	
	public static Product create(String name, Long price, Integer stock) {
		Product product = new Product();
		
		product.validatePrice(price);
		product.validateName(name);
		product.validateStock(stock);
		
		product.name = name;
		product.price = price;
		product.stock = stock;
		
		product.status = ProductStatus.SELLING;
		product.deletedAt = null;
		
		return product;
	}
	
	public static Product create(ProductCreateRequest productCreate) {
		Product product = new Product();
		
		product.validatePrice(productCreate.price());
		product.validateName(productCreate.name());
		product.validateStock(productCreate.stock());
		
		product.name = productCreate.name();
		product.price = productCreate.price();
		product.stock = productCreate.stock();
		
		product.status = ProductStatus.SELLING;
		product.deletedAt = null;
		
		return product;
	}
	
	// ===== Method =====
	
	// 1. 상품 이름 수정
	public void changeName(String newName) {
		validateDeleteState();
		validateName(newName);
		
		this.name = newName;
	}
	
	// 2. 상품 가격 수정
	public void changePrice(Long price) {
		validateDeleteState();
		validatePrice(price);
		
		this.price = price;
	}
	
	// 3. 상품 재고 설정
	public void changeStock(Integer stock) {
		validateDeleteState();
		validateStock(stock);
		
		this.stock = stock;
	}
	
	// 4. 상품 판매 중지 처리
	public void discountinue() {
		validateDeleteState();
		
		if(this.status == ProductStatus.DISCOUNTINUED) {
			return;
		}
		
		this.status = ProductStatus.DISCOUNTINUED;
	}
	
	// 5. 상품 판매 개시
	public void selling() {
		validateDeleteState();
		
		if(this.status == ProductStatus.SELLING) {
			return;
		}
		
		this.status = ProductStatus.SELLING;
	}
	
	// 6. 상품 삭제
	public void delete(Instant deletedAt) {
		if(this.deletedAt != null) {
			throw new IllegalStateException("이미 삭제된 상품입니다.");
		}
		
		this.deletedAt = deletedAt;
	}
	
	// 7. 주문이 가능한 상태인가?
	/** 주문이 가능한 경우 TRUE 반환 */
	public boolean isOrderable() {
		return !isDeleted()														// 삭제 되지 않았고
				&& this.status == ProductStatus.SELLING		// 판매중인 상태이며
				&& this.stock > 0;												// 재고가 1 이상인 상품이면 TRUE
	}
	
	// 8. 상품이 삭제되었는가?
	/** 삭제된 상품의 경우 TRUE 반환 */
	public boolean isDeleted() {
		if(this.deletedAt == null) {
			return false;
		}
		
		return true;
	}
	
// OrderCommandService createOrder 수정으로 인해 필요없어짐.
//	public void orderStockDecrease(Integer quantity) {
//		this.stock -= quantity;
//		
//		if(this.stock < 0) {
//			throw new IllegalArgumentException("주문 수량에 비해 재고가 부족하여 주문이 취소되었습니다.");
//		}
//	}
	
	// ===== Private Method =====
	
	// 1. 가격 검증
	private void validatePrice(Long price) {
		if(price == null || price <= 0) {
			throw new IllegalArgumentException("상품의 가격은 0보다 커야합니다.");
		}
	}
	
	// 2. 상품명 검증
	private void validateName(String name) {
		if(name == null || name.isBlank()) {
			throw new IllegalArgumentException("상품명은 비어있을 수 없습니다.");
		}
	}
	
	// 3. 상품 재고 검증
	private void validateStock(Integer stock) {
		if(stock == null || stock < 0) {
			throw new IllegalArgumentException("상품 재고는 0이상이어야 합니다.");
		}
	}
	
	// 4. 상품 삭제 상태 검증
	private void validateDeleteState() {
		if(isDeleted()) {
			throw new IllegalStateException("삭제된 상품입니다.");
		}
	}
	
}
