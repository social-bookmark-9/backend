package com.sparta.backend.requestDto;

import com.sparta.backend.model.ArticleFolder;
import com.sparta.backend.model.Hashtag;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

@Getter
@RequiredArgsConstructor
@Builder
public class ArticleCreateRequestDto {
    @URL(message = "올바른 URL형식이 아닙니다.")
    private final String url;
    @NotBlank(message = "잘못된 입력입니다.")
    private final String titleOg;
    @NotBlank(message = "잘못된 입력입니다.")
    private final String imgOg;
    private final String review;
    private final Boolean reviewHide;
    private final int readCount;
    @NotBlank(message = "잘못된 입력입니다.")
    private final Hashtag hashtag;
    @NotBlank(message = "잘못된 입력입니다.")
    private final ArticleFolder articleFolder;
}
