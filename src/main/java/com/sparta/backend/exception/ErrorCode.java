
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

    // 회원가입 관련 에러 모음
    USERNAME_VALIDATE(HttpStatus.BAD_REQUEST, "400_Register_1", "유저네임은 최소 3자 이상, 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성해야 합니다.");
    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String errorMessage;
}