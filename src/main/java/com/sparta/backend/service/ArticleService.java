package com.sparta.backend.service;

import com.sparta.backend.model.Member;
import com.sparta.backend.requestDto.*;
import com.sparta.backend.responseDto.*;

public interface ArticleService {
    ArticleCreateResponseDto createArticle(ArticleCreateRequestDto requestDto, Member member);
    ArticleGetResponseDto getArticleForMember(Long id, Member member);
    ArticleGetResponseDto getArticleForGuest(Long id);
    void deleteArticle(Long id, Member member);
    ArticleTitleResponseDto updateArticleTitle(ArticleTitleRequestDto requestDto, Long id, Member member);
    void updateArticleHashtag(HashtagUpdateRequestDto requestDto, Long id, Member member);
    ArticleReviewResponseDto updateArticleReview(ArticleReviewRequestDto requestDto, Long id, Member member);
    ArticleReviewHideResponseDto updateArticleReviewHide(Long id, Member member);
    ArticleReviewResponseDtos getArticleReviews(Member member);
    void addArticleReadCount(Long id, Member member);
    void updateArticleFolderChange(ArticleFolderChangeUpdateRequestDto requestDto, Long id, Member member);
    void saveAllArticlesByOtherUser(ArticleFolderChangeUpdateRequestDto requestDto, Long id, Member member);
}