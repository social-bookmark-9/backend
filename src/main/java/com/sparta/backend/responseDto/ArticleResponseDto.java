package com.sparta.backend.responseDto;

import com.sparta.backend.model.ArticleFolder;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArticleResponseDto {
    private final String hashtag1;
    private final String hashtag2;
    private final String hashtag3;
    private final String titleOg;
    private final String contentOg;
    private final String review;
    private final boolean reviewHide;
    private final int readCount;
    private final ArticleFolder articleFolder;
}
