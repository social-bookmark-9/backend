package com.sparta.backend.requestDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OGTagRequestDto {
    private String titleOg;
    private String imgOg;
    private String contentOg;
}
