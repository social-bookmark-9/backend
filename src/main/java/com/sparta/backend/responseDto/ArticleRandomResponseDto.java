package com.sparta.backend.responseDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArticleRandomResponseDto {
    private final Long articleId;
    private final String titleOg;
    private final String imgOg;
    private final String contentOg;
    private final String hashtag1;
    private final String hashtag2;
    private final String hashtag3;
}
