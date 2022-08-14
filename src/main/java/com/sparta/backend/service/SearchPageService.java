package com.sparta.backend.service;

import com.sparta.backend.responseDto.MainAndSearchPageArticleFolderResponseDto;
import com.sparta.backend.responseDto.SearchPageArticleResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


public interface SearchPageService {
    Slice<MainAndSearchPageArticleFolderResponseDto> searchArticleFolders(String hashtag, String keyword, Pageable pageable);
    Slice<SearchPageArticleResponseDto> searchArticles(String hashtag, String keyword, Pageable pageable);
}
