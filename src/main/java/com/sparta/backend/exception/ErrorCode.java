
package com.sparta.backend.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Optional;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // 400 Bad Request - 잘못된 요청 (REQUEST 가 잘못됨).
    // 403 Forbidden - 해당 요청에 대한 권한이 없음.
    // 404 Not Found - 해당 RESOURECE 를 찾을 수 없음.

    // 아티클 생성 관련 에러 모음
    ARTICLE_BOOLEAN_VALIDATE(HttpStatus.BAD_REQUEST, "400_Article_2", "2번째. (Boolean)"),
    ARTICLE_URL_VALIDATE(HttpStatus.BAD_REQUEST, "400_Article_1", "올바른 URL 형식이 아닙니다.");


    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String errorMessage;
}