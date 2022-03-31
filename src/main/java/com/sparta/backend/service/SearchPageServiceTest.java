package com.sparta.backend.service;

import com.sparta.backend.responseDto.ArticleRandomResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchPageServiceTest {

    List<ArticleRandomResponseDto> getSearchArticles(String hashtag, String titleOg, Pageable pageable);
}
