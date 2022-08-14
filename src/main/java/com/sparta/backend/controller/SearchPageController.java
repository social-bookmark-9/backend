package com.sparta.backend.controller;

import com.sparta.backend.message.RestResponseMessage;
import com.sparta.backend.responseDto.MainAndSearchPageArticleFolderResponseDto;
import com.sparta.backend.responseDto.SearchPageArticleResponseDto;
import com.sparta.backend.service.SearchPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@RestController
@RequiredArgsConstructor
public class SearchPageController {

    private final SearchPageService searchPageService;

    @GetMapping("/api/searchpage/articlefolderstest")
    public ResponseEntity<RestResponseMessage<?>> searchArticleFolders(@RequestParam(required = false, defaultValue = "") String hashtag, @RequestParam(required = false, defaultValue = "") String keyword, @RequestParam(defaultValue = "1") int page) throws UnsupportedEncodingException {
        if (page < 1) page = 1;
        PageRequest pageRequest = PageRequest.of(page - 1, 6, Sort.unsorted());

        if (!hashtag.trim().isEmpty()) hashtag = URLDecoder.decode(hashtag, "UTF-8");
        if (!keyword.trim().isEmpty()) keyword = URLDecoder.decode(keyword, "UTF-8");

        Slice<MainAndSearchPageArticleFolderResponseDto> articleFolders = searchPageService.searchArticleFolders(hashtag, keyword, pageRequest);

        return new ResponseEntity<>(new RestResponseMessage<>(true, "아티클 폴더 검색 결과", articleFolders), HttpStatus.OK);
    }

    @GetMapping("/api/searchpage/articlestest")
    public ResponseEntity<RestResponseMessage<?>> searchArticles(@RequestParam(required = false, defaultValue = "") String hashtag, @RequestParam(required = false, defaultValue = "") String keyword, @RequestParam(defaultValue = "1") int page) throws UnsupportedEncodingException {
        if (page < 1) page = 1;

        PageRequest pageRequest = PageRequest.of(page - 1, 6, Sort.unsorted());

        if (!hashtag.trim().isEmpty()) hashtag = URLDecoder.decode(hashtag, "UTF-8");
        if (!keyword.trim().isEmpty()) keyword = URLDecoder.decode(keyword, "UTF-8");

        Slice<SearchPageArticleResponseDto> articles = searchPageService.searchArticles(hashtag, keyword, pageRequest);

        return new ResponseEntity<>(new RestResponseMessage<>(true, "아티클 검색 결과", articles), HttpStatus.OK);
    }
}
