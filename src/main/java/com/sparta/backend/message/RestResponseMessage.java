package com.sparta.backend.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RestResponseMessage<T> {
    private final boolean status;
    private final String message;
    private final T data;
}