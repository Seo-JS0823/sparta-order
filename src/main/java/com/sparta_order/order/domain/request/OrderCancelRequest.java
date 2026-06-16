package com.sparta_order.order.domain.request;

public record OrderCancelRequest(Long orderId, String reason) {

}
