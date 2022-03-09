package com.sparta.backend.requestDto;

import com.sparta.backend.model.ArticleFolder;
import com.sparta.backend.model.Hashtag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ArticleCreateRequestDto {
    private final String url;
    private final String titleOg;
    private final String imgOg;
    private final String review;
    private final Boolean reviewHide;
    private final int readCount;
    private final Hashtag hashtag;
    private final ArticleFolder articleFolder;
}
