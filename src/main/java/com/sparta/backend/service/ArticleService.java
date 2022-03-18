package com.sparta.backend.service;

import com.sparta.backend.model.Member;
import com.sparta.backend.requestDto.ArticleCreateRequestDto;
import com.sparta.backend.requestDto.ArticleReviewRequestDto;
import com.sparta.backend.requestDto.ArticleUpdateRequestDto;
import com.sparta.backend.responseDto.ArticleCreateResponseDto;
import com.sparta.backend.responseDto.ArticleGetResponseDto;
import com.sparta.backend.responseDto.ArticleReviewHideResponseDto;
import com.sparta.backend.responseDto.ArticleReviewResponseDto;

public interface ArticleService {
    ArticleGetResponseDto getArticle(Long id, Member member);
    ArticleCreateResponseDto createArticle(ArticleCreateRequestDto requestDto, Member member);
    void updateArticle(ArticleUpdateRequestDto requestDto, Long id, Member member);
    ArticleReviewResponseDto updateArticleReview(ArticleReviewRequestDto requestDto, Long id, Member member);
    ArticleReviewHideResponseDto updateArticleReviewHide(Long id);
}