package com.sparta.backend.exception;

public class ArticleAccessDeniedException extends BusinessException {

    public ArticleAccessDeniedException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public ArticleAccessDeniedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
