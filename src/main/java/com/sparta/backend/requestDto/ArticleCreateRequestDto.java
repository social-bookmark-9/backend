package com.sparta.backend.requestDto;

import com.sparta.backend.model.ArticleFolder;
import com.sparta.backend.model.Hashtag;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class ArticleCreateRequestDto {
    @URL(message = "올바른 URL형식이 아닙니다.")
    private final String url;

    @NotBlank(message = "titleOg가 없습니다.")
    private final String titleOg;

    @NotBlank(message = "imgOg가 없습니다.")
    private final String imgOg;

    @NotBlank(message = "contentOg가 없습니다.")
    private final String contentOg;

    private final String review = "";
    private final boolean reviewHide = false;
    private final int readCount = 0;
    private final int buttonDate;

    @NotBlank(message = "한 개 이상의 태그를 선택해야 합니다.")
    private final Hashtag hashtag;

    @NotBlank(message = "아티클 폴더를 반드시 선택해야 합니다.")
    private final ArticleFolder articleFolder;
}
