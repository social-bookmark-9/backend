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

import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class SearchPageControllerTest {

    private final SearchPageServiceTest searchPageServiceTest;

    // 아티클 해시태그 검색 최신순으로 가져오기
    @GetMapping("/api/searchpage/articles")
    public ResponseEntity<RestResponseMessage> getArticles(@RequestParam String hashtag, @RequestParam String titleOg, @RequestParam int page, @RequestParam String sort) {

        PageRequest pageRequest = PageRequest.of(page, 6, Sort.by(Sort.Direction.DESC, sort));

        // 해쉬태그 디코드
        byte[] hashtagDecoded = Base64.getDecoder().decode(hashtag);
        hashtag = new String(hashtagDecoded, StandardCharsets.UTF_8);

        // 검색어 디코드
        byte[] titleOgDecoded = Base64.getDecoder().decode(titleOg);
        titleOg = new String(titleOgDecoded, StandardCharsets.UTF_8);

        Map<String, Object> articleList = searchPageServiceTest.getSearchArticles(hashtag, titleOg, pageRequest);

        return new ResponseEntity<>(new RestResponseMessage<>(true,"아티클 검색 결과", articleList), HttpStatus.OK);
    }
    
    // 아티클 폴더 해시태그 검색 최신순, 좋아요 순
    @GetMapping("/api/searchpage/articlefolders")
    public ResponseEntity<RestResponseMessage> getArticleFolders(@RequestParam String hashtag, @RequestParam String titleOg, @RequestParam int page, @RequestParam String sort) {

        PageRequest pageRequest = PageRequest.of(page, 6, Sort.by(Sort.Direction.DESC, sort));

        // 해쉬태그 디코드
        byte[] hashtagDecoded = Base64.getDecoder().decode(hashtag);
        hashtag = new String(hashtagDecoded, StandardCharsets.UTF_8);

        // 검색어 디코드
        byte[] titleOgDecoded = Base64.getDecoder().decode(titleOg);
        titleOg = new String(titleOgDecoded, StandardCharsets.UTF_8);

        Map<String, Object> articleFolderList = searchPageServiceTest.getSearchArticleFolders(hashtag, titleOg, pageRequest);

        return new ResponseEntity<>(new RestResponseMessage<>(true,"아티클 폴더 검색 결과", articleFolderList), HttpStatus.OK);
    }

}
