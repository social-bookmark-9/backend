package com.sparta.backend.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class DataListMessage<T> {
    private final String message;
    private final List<T> dataList;
}
