package com.sparta_order._global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseCode {

	SUCCESS("SUCCESS", "요청이 정상적으로 처리되었습니다."),
	CREATED("CREATED", "생성이 정상적으로 처리되었습니다."),
	UPDATED("UPDATED", "수정이 정상적으로 처리되었습니다."),
	DELETED("DELETED", "삭제가 정상적으로 처리되었습니다."),
	
	FAIL("FAIL", "요청 처리에 실패하였습니다."),
	
	
	;
	private final String code;
	
	private final String message;
}
