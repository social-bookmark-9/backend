/*
package com.sparta.backend.controller;

import com.sparta.backend.message.RestResponseMessage;
import com.sparta.backend.model.Member;
import com.sparta.backend.responseDto.ArticleRandomResponseDto;
import com.sparta.backend.service.MainPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MainPageArticleController {

    private final MainPageService mainPageService;

    @GetMapping("/api/mainpage/hashtags")
    public ResponseEntity<RestResponseMessage> getArticlesSearchByHashtag(@AuthenticationPrincipal Member getMember, @RequestParam String hashtag) throws UnsupportedEncodingException {

        // hashtag 디코딩
        hashtag = URLDecoder.decode(hashtag, "UTF-8");

        List<ArticleRandomResponseDto> articleList = mainPageService.getMonthArticlesLogin(getMember, hashtag);

        return new ResponseEntity<>(new RestResponseMessage<>(true,"태그 검색 결과", articleList), HttpStatus.OK);
    }
}
*/
