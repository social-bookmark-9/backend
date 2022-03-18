package com.sparta.backend.message;

import com.nimbusds.oauth2.sdk.ErrorResponse;
import com.sparta.backend.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponseMessage {
    private final boolean status = false;
    private int statusCode;
    private String message;
    private List<FieldError> errors;
    private String code;

    // Valid
    private ErrorResponseMessage(final ErrorCode code, final List<FieldError> errors) {
        this.statusCode = code.getStatusCode();
        this.message = code.getErrorMessage();
        this.errors = errors;
        this.code = code.getErrorCode();
    }

    // 일반적으로
    private ErrorResponseMessage(final ErrorCode code) {
        this.statusCode = code.getStatusCode();
        this.message = code.getErrorMessage();
        this.errors = new ArrayList<>();
        this.code = code.getErrorCode();
    }

    public static ErrorResponseMessage of(final ErrorCode code, final BindingResult bindingResult) {
        return new ErrorResponseMessage(code, FieldError.of(bindingResult));
    }

    public static ErrorResponseMessage of(final ErrorCode code) {
        return new ErrorResponseMessage(code);
    }

    public static ErrorResponseMessage of(final ErrorCode code, final List<FieldError> errors) {
        return new ErrorResponseMessage(code, errors);
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class FieldError {
        private String field;
        private String value;
        private String reason;

        private FieldError(String field, String value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }

        public static List<FieldError> of(final String field, final String value, final String reason) {
            List<FieldError> fieldErrors = new ArrayList<>();
            fieldErrors.add(new FieldError(field, value, reason));
            return fieldErrors;
        }

        private static List<FieldError> of(final BindingResult bindingResult) {
            final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
            return fieldErrors.stream()
                    .map(error -> new FieldError(
                            error.getField(),
                            error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(),
                            error.getDefaultMessage()))
                    .collect(Collectors.toList());
        }

    }
}