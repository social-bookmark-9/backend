package com.sparta.backend.service;

import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface SearchPageServiceTest {
    Map<String, Object> getSearchArticles(String hashtag, String titleOg, Pageable pageable);
    Map<String, Object> getSearchArticleFolders(String hashtag, String articleFolderName, Pageable pageable);
}
