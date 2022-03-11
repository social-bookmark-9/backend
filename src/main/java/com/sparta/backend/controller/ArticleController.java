package com.sparta.backend.controller;

import com.sparta.backend.message.DefaultMessage;
import com.sparta.backend.model.Member;
import com.sparta.backend.requestDto.ArticleCreateRequestDto;
import com.sparta.backend.service.ArticleService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class ArticleController {

    private final ArticleService articleService;

    @ApiOperation(value = "아티클 생성", notes = "아티클을 생성하기 위한 API")
    @PostMapping("/articles")
    public ResponseEntity<DefaultMessage> createArticles(@Valid @RequestBody ArticleCreateRequestDto requestDto,
                                                         @AuthenticationPrincipal Member member) {
        articleService.createArticle(requestDto, member);
        return new ResponseEntity<>(new DefaultMessage("북마크를 저장했습니다."), HttpStatus.OK);
    }
}
