package com.sparta_order._global.response;

import java.time.Instant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApiResponse<T> {

	private final String code;
	
	private final String message;
	
	private final T data;
	
	private final Instant timeStamp;
	
	public static ApiResponse<Void> response(ResponseCode responseCode) {
		return new ApiResponse<>(
				responseCode.getCode(),
				responseCode.getMessage(),
				null,
				Instant.now()
		);
	}
	
	public static <T> ApiResponse<T> response(ResponseCode responseCode, T data) {
		return new ApiResponse<>(
				responseCode.getCode(),
				responseCode.getMessage(),
				data,
				Instant.now()
		);
	}
	
}
