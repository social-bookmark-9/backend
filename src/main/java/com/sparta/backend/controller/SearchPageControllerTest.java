package com.sparta.backend.controller;

import com.sparta.backend.message.RestResponseMessage;
import com.sparta.backend.responseDto.ArticleRandomResponseDto;
import com.sparta.backend.service.SearchPageServiceTest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SearchPageControllerTest {

    private final SearchPageServiceTest searchPageServiceTest;

    // 아티클 해시태그 검색 최신순으로 가져오기
    @GetMapping("/api/searchpage/hashtags")
    public ResponseEntity<RestResponseMessage> getArticles(@RequestParam String hashtag, @RequestParam String titleOg, @RequestParam int page) {

        PageRequest pageRequest = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "createdAt"));
        
        List<ArticleRandomResponseDto> articleList = searchPageServiceTest.getSearchArticles(hashtag, titleOg, pageRequest);

        return new ResponseEntity<>(new RestResponseMessage<>(true,"태그 검색 결과", articleList), HttpStatus.OK);
    }
    
    // 아티클 폴더 해시태그 검색 최신순, 좋아요 순

}
