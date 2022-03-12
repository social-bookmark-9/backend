package com.sparta.backend.service;

import com.sparta.backend.model.Article;
import com.sparta.backend.model.Member;
import com.sparta.backend.requestDto.ArticleCreateRequestDto;
import com.sparta.backend.requestDto.ArticleUpdateRequestDto;

public interface ArticleService {
    Article getArticle(Long id);
    Long createArticle(ArticleCreateRequestDto requestDto, Member member);
    void updateArticle(ArticleUpdateRequestDto requestDto, Member member, Long id);
}
