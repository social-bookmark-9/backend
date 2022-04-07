package com.sparta.backend.controller;

import com.sparta.backend.message.RestResponseMessage;
import com.sparta.backend.service.SearchPageServiceTest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class SearchPageControllerTest {

    private final SearchPageServiceTest searchPageServiceTest;

    // 아티클 해시태그 검색 최신순으로 가져오기
    @GetMapping("/api/searchpage/articles")
    public ResponseEntity<RestResponseMessage> getArticles(@RequestParam String hashtag, @RequestParam String titleOg, @RequestParam int page, @RequestParam String sort) throws UnsupportedEncodingException {

        PageRequest pageRequest = PageRequest.of(page, 6, Sort.by(Sort.Direction.DESC, sort));

        // titleOg 디코딩
        titleOg = URLDecoder.decode(titleOg, "UTF-8");

        // hashtag 디코딩
        hashtag = URLDecoder.decode(hashtag, "UTF-8");

        Map<String, Object> articleList = searchPageServiceTest.getSearchArticles(hashtag, titleOg, pageRequest);

        return new ResponseEntity<>(new RestResponseMessage<>(true,"아티클 검색 결과", articleList), HttpStatus.OK);
    }
    
    // 아티클 폴더 해시태그 검색 최신순, 좋아요 순
    @GetMapping("/api/searchpage/articlefolders")
    public ResponseEntity<RestResponseMessage> getArticleFolders(@RequestParam String hashtag, @RequestParam String titleOg, @RequestParam int page, @RequestParam String sort) throws UnsupportedEncodingException {

        PageRequest pageRequest = PageRequest.of(page, 6, Sort.by(Sort.Direction.DESC, sort));

        // titleOg 디코딩
        titleOg = URLDecoder.decode(titleOg, "UTF-8");

        // hashtag 디코딩
        hashtag = URLDecoder.decode(hashtag, "UTF-8");

        Map<String, Object> articleFolderList = searchPageServiceTest.getSearchArticleFolders(hashtag, titleOg, pageRequest);

        return new ResponseEntity<>(new RestResponseMessage<>(true,"아티클 폴더 검색 결과", articleFolderList), HttpStatus.OK);
    }


}
