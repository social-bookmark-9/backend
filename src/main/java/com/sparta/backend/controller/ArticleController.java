package com.sparta.backend.controller;

import com.sparta.backend.message.DataMessage;
import com.sparta.backend.message.DefaultMessage;
import com.sparta.backend.model.Article;
import com.sparta.backend.model.Member;
import com.sparta.backend.requestDto.ArticleCreateRequestDto;
import com.sparta.backend.requestDto.ArticleUpdateRequestDto;
import com.sparta.backend.service.ArticleService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class ArticleController {

    private final ArticleService articleService;

    // TODO: API 추가됨
    @ApiOperation(value = "특정 아티클 조회", notes = "특정 아티클 조회 API")
    @GetMapping("/articles/{id}")
    public ResponseEntity<DataMessage<Article>> getArticle(@Valid @PathVariable Long id) {
        Article findArticle = articleService.getArticle(id);
        return new ResponseEntity<>(new DataMessage<>("링크를 성공적으로 조회했습니다.", findArticle), HttpStatus.OK);
    }

    // TODO: 리마인더 여부 추가 예정
    @ApiOperation(value = "아티클 생성", notes = "아티클 생성 API")
    @PostMapping("/articles")
    public ResponseEntity<DefaultMessage> createArticles(@Valid @RequestBody ArticleCreateRequestDto requestDto,
                                                         @AuthenticationPrincipal Member member) {
        articleService.createArticle(requestDto, member);
        return new ResponseEntity<>(new DefaultMessage("링크가 추가되었습니다."), HttpStatus.OK);
    }

    @ApiOperation(value = "아티클 수정", notes = "아티클 수정 API")
    @PatchMapping("/articles/{id}")
    public ResponseEntity<DefaultMessage> updateArticles(@Valid @RequestBody ArticleUpdateRequestDto requestDto,
                                                         @PathVariable Long id,
                                                         @AuthenticationPrincipal Member member) {
        articleService.updateArticle(requestDto, member, id);
        return new ResponseEntity<>(new DefaultMessage("링크가 수정되었습니다."), HttpStatus.OK);
    }
}
