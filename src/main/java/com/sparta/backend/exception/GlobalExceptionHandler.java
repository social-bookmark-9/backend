package com.sparta.backend.exception;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.sparta.backend.message.ErrorResponseMessage;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponseMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException", e);
        final ErrorResponseMessage responseMessage = ErrorResponseMessage.of(ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult());
        return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponseMessage> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("HttpRequestMethodNotSupportedException", e);
        final ErrorResponseMessage responseMessage = ErrorResponseMessage.of(ErrorCode.METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(responseMessage, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponseMessage> handleAccessDeniedException(AccessDeniedException e) {
        log.error("handleAccessDeniedException", e);
        final ErrorResponseMessage responseMessage = ErrorResponseMessage.of(ErrorCode.HANDLE_ACCESS_DENIED);
        // TODO: valueOf ??
        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(ErrorCode.HANDLE_ACCESS_DENIED.getStatusCode()));
    }

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponseMessage> handleBusinessException(final BusinessException e) {
        log.error("handleEntityNotFoundException", e);
        final ErrorCode errorCode = e.getErrorCode();
        final ErrorResponseMessage responseMessage = ErrorResponseMessage.of(errorCode);
        return new ResponseEntity<>(responseMessage, HttpStatus.valueOf(errorCode.getStatusCode()));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponseMessage> handleException(Exception e) {
        log.error("handleEntityNotFoundException", e);
        final ErrorResponseMessage responseMessage = ErrorResponseMessage.of(ErrorCode.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(responseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
