package com.sparta.backend.service;

import com.sparta.backend.model.Member;
import com.sparta.backend.requestDto.ArticleCreateRequestDto;

public interface ArticleService {
    void createArticle(ArticleCreateRequestDto requestDto, Member member);
}
