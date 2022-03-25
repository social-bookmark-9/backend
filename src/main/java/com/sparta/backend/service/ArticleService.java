package com.sparta.backend.service;

import com.sparta.backend.model.Member;
import com.sparta.backend.requestDto.*;
import com.sparta.backend.responseDto.*;

public interface ArticleService {
    ArticleCreateResponseDto createArticle(ArticleCreateRequestDto requestDto, Member member);
    ArticleGetResponseDto getArticleForMember(Long id, Member member);
    ArticleGetResponseDto getArticleForGuest(Long id);
    void deleteArticle(Long id, Member member);
    ArticleTitleResponseDto updateTitle(ArticleTitleRequestDto requestDto, Long id, Member member);
    void updateHashtag(HashtagUpdateRequestDto requestDto, Long id, Member member);
    ArticleReviewResponseDto updateArticleReview(ArticleReviewRequestDto requestDto, Long id, Member member);
    ArticleReviewHideResponseDto updateArticleReviewHide(Long id, Member member);
    ArticleReviewResponseDtos getReviews(Member member);
    void addReadCount(Long id, Member member);
    void moveMyArticleToAnotherFolder(ArticleUpdateRequestDto requestDto, Long id, Member member);
}