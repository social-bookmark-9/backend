package com.sparta.backend.requestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HashtagUpdateRequestDto {
    @NotBlank(message = "한 개 이상의 태그를 선택해야 합니다.")
    private String hashtag1;
    private String hashtag2;
    private String hashtag3;
}
