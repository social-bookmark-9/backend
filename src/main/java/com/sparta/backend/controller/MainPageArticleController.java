package com.sparta.backend.controller;

import com.sparta.backend.message.RestResponseMessage;
import com.sparta.backend.model.Article;
import com.sparta.backend.repository.ArticleRepository;
import com.sparta.backend.responseDto.ArticleRandomResponseDto;
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

    private final ArticleRepository articleRepository;

    @GetMapping("/api/mainpage/hashtags")
    public ResponseEntity<RestResponseMessage> getArticlesSearchByHashtag(@RequestParam String hashtag) {

        LocalDateTime startDatetime = LocalDateTime.of(LocalDate.now().minusDays(30), LocalTime.of(0,0,0));
        LocalDateTime endDatetime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59));

        RandomGenerator randomGenerator = new RandomGenerator();
        List<Article> articles = articleRepository.findArticlesByHashtag_Hashtag1AndCreatedAtBetween(hashtag, startDatetime, endDatetime);
        List<Article> randomArticles;
        if (articles.size() > 5) { randomArticles = randomGenerator.getRandomArticles(articles, 6); }
        else { randomArticles = articles; }

        List<ArticleRandomResponseDto> randomResponseDtos = new ArrayList<>();
        for (Article randomArticle : randomArticles) {
            ArticleRandomResponseDto responseDto = ArticleRandomResponseDto.builder()
                    .articleId(randomArticle.getId())
                    .titleOg(randomArticle.getTitleOg())
                    .imgOg(randomArticle.getImgOg())
                    .contentOg(randomArticle.getContentOg())
                    .hashtag1(randomArticle.getHashtag().getHashtag1())
                    .hashtag2(randomArticle.getHashtag().getHashtag2())
                    .hashtag3(randomArticle.getHashtag().getHashtag3())
                    .build();
            randomResponseDtos.add(responseDto);
        }

        return new ResponseEntity<>(new RestResponseMessage<>(true,"태그 검색 결과", randomResponseDtos), HttpStatus.OK);
    }
}
