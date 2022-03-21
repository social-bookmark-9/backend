
package com.sparta.backend.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Common
    ARTICLE_BOOLEAN_VALIDATE(400, HttpStatus.BAD_REQUEST, "400", "2번째. (Boolean)"),
    INVALID_INPUT_VALUE(400, HttpStatus.BAD_REQUEST, "400", "입력이 올바르지 않습니다."),
    METHOD_NOT_ALLOWED(405, HttpStatus.METHOD_NOT_ALLOWED, "405", "유효한 메서드가 아닙니다."),
    HANDLE_ACCESS_DENIED(403, HttpStatus.FORBIDDEN, "403", "권한이 없습니다."),
    INTERNAL_SERVER_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "500", "서버 오류"),
    ENTITY_NOT_FOUND(400, HttpStatus.BAD_REQUEST, "400", "요청한 데이터를 찾을 수 없습니다."),

    // Token
    REFRESH_TOKEN_EXPIRED(401, HttpStatus.UNAUTHORIZED, "401", "리프레시 토큰이 만료되었습니다.");

    private final int statusCode;
    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String errorMessage;
}