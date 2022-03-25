package com.sparta.backend.service;

import com.sparta.backend.model.Member;
import com.sparta.backend.responseDto.ArticleFolderListResponseDto;
import com.sparta.backend.responseDto.ArticleRandomResponseDto;
import com.sparta.backend.responseDto.RecommendedMemberResponseDto;

import java.util.List;

public interface MainPageService {

    List<RecommendedMemberResponseDto> getRecommendedMembers(Member getMember, String randomHashtag);

    List<ArticleRandomResponseDto> getMonthArticles(String hashtag);

    List<ArticleFolderListResponseDto> getRecommendedArticleFolders(Member getMember);
}
