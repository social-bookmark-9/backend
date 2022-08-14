package com.sparta.backend.service;

import com.sparta.backend.model.Member;
import com.sparta.backend.responseDto.ArticleRandomResponseDto;
import com.sparta.backend.responseDto.MainAndSearchPageArticleFolderResponseDto;
import com.sparta.backend.responseDto.MemberHashtagInfoDto;
import com.sparta.backend.responseDto.RecommendedMemberResponseDto;

import java.util.List;

public interface MainPageService {
    List<RecommendedMemberResponseDto> getRecommendedMembersLogin(Member member, MemberHashtagInfoDto memberHashtagInfoDto);
    List<ArticleRandomResponseDto> getMonthArticlesLogin(Member member, MemberHashtagInfoDto memberHashtagInfoDto);
    List<MainAndSearchPageArticleFolderResponseDto> getRecommendedArticleFoldersLogin(Member member, MemberHashtagInfoDto memberHashtagInfoDto);

    List<RecommendedMemberResponseDto> getRecommendedMembersNonLogin(String hashtag);
    List<ArticleRandomResponseDto> getMonthArticlesNonLogin();
    List<MainAndSearchPageArticleFolderResponseDto> getRecommendedArticleFoldersNonLogin(String hashtag);
}
