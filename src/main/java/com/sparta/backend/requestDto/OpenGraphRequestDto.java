package com.sparta.backend.requestDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OpenGraphRequestDto {
    private String titleOg;
    private String imgOg;
    private String contentOg;
}
