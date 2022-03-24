package com.sparta.backend.controller;

import com.sparta.backend.message.RestResponseMessage;
import com.sparta.backend.model.Article;
import com.sparta.backend.repository.ArticleRepository;
import com.sparta.backend.responseDto.ArticleRandomResponseDto;
import com.sparta.backend.service.ArticleService;
import com.sparta.backend.service.MainPageService;
import com.sparta.backend.utils.RandomGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MainPageArticleController {

    private final MainPageService mainPageService;

    @GetMapping("/api/mainpage/hashtags")
    public ResponseEntity<RestResponseMessage> getArticlesSearchByHashtag(@RequestParam String hashtag) {

        List<ArticleRandomResponseDto> articleList = mainPageService.getMonthArticles(hashtag);

        return new ResponseEntity<>(new RestResponseMessage<>(true,"태그 검색 결과", articleList), HttpStatus.OK);
    }
}
