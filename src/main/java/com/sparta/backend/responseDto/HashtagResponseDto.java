package com.sparta.backend.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HashtagResponseDto {
    private String hashtag1;
    private String hashtag2;
    private String hashtag3;
}
