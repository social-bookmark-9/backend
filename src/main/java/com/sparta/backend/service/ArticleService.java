package com.sparta.backend.service;

import com.sparta.backend.requestDto.ArticleCreateRequestDto;
import com.sparta.backend.requestDto.ArticleReviewRequestDto;
import com.sparta.backend.requestDto.ArticleUpdateRequestDto;
import com.sparta.backend.responseDto.ArticleResponseDto;
import com.sparta.backend.responseDto.ArticleReviewHideResponseDto;
import com.sparta.backend.responseDto.ArticleReviewResponseDto;

public interface ArticleService {
    ArticleResponseDto getArticle(long id);
    long createArticle(ArticleCreateRequestDto requestDto);
    void updateArticle(ArticleUpdateRequestDto requestDto, long id);
    ArticleReviewResponseDto updateArticleReview(ArticleReviewRequestDto requestDto, long id);
    ArticleReviewHideResponseDto updateArticleReviewHide(long id);
}
