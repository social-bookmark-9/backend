package com.sparta.backend.service;

import com.sparta.backend.model.Member;
import com.sparta.backend.requestDto.ArticleCreateRequestDto;
import com.sparta.backend.requestDto.ArticleReviewRequestDto;
import com.sparta.backend.requestDto.ArticleUpdateRequestDto;
import com.sparta.backend.responseDto.ArticleResponseDto;
import com.sparta.backend.responseDto.ArticleReviewResponseDto;

public interface ArticleService {
    ArticleResponseDto getArticle(long id, Member member);
    long createArticle(ArticleCreateRequestDto requestDto, Member member);
    void updateArticle(ArticleUpdateRequestDto requestDto, long id, Member member);
    ArticleReviewResponseDto updateArticleReview(ArticleReviewRequestDto requestDto, long id, Member member);
    boolean updateArticleReviewHide(long id);
}