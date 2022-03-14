package com.sparta.backend.requestDto;

import com.sparta.backend.model.ArticleFolder;
import com.sparta.backend.model.Hashtag;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class ArticleUpdateRequestDto {
    private final String titleOg;
    private final String review;
    private final boolean reviewHide;

    @NotBlank(message = "한 개 이상의 태그를 선택해야 합니다.")
    private final Hashtag hashtag;

    @NotBlank(message = "아티클 폴더를 반드시 선택해야 합니다.")
    private final ArticleFolder articleFolder;
}
