package com.sparta.backend.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class DataMessage<T> {
    private final String message;
    private final T data;
}