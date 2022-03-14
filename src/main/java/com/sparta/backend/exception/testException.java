package com.sparta.backend.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class testException {

    //    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
//    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException e) {
//        Map<String, String> errors = new HashMap<>();
//        e.getBindingResult().getAllErrors()
//                .stream()
//                .forEach(
//                        error -> errors.put(((FieldError) error).getField(), error.getDefaultMessage())
//                );
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
//    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<DefaultMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();

        return new ResponseEntity<>(new DefaultMessage(errorMessage), HttpStatus.BAD_REQUEST);
    }
}
