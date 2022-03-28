package com.sparta.backend.exception;

public class InvalidValueException extends BusinessException {

    public InvalidValueException(String message) {
        super(message, ErrorCode.INVALID_INPUT_VALUE);
    }
    public InvalidValueException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
    public InvalidValueException(ErrorCode errorCode) {
        super(errorCode);
    }
}
