package com.sparta.backend.controller;

import com.sparta.backend.message.RestResponseMessage;
import com.sparta.backend.model.Member;
import com.sparta.backend.requestDto.ArticleCreateRequestDto;
import com.sparta.backend.requestDto.ArticleReviewRequestDto;
import com.sparta.backend.requestDto.ArticleUpdateRequestDto;
import com.sparta.backend.responseDto.ArticleCreateResponseDto;
import com.sparta.backend.responseDto.ArticleGetResponseDto;
import com.sparta.backend.responseDto.ArticleReviewHideResponseDto;
import com.sparta.backend.responseDto.ArticleReviewResponseDto;
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

    // ✅️ 특정 아티클 조회
    @ApiOperation(value = "특정 아티클 조회", notes = "특정 아티클 조회 API")
    @GetMapping("/articles/{articleId}")
    public ResponseEntity<RestResponseMessage<?>> getArticle(@Valid @PathVariable long articleId,
                                                                   @AuthenticationPrincipal Member member) {
        ArticleGetResponseDto responseDto = articleService.getArticle(articleId, member);
        return new ResponseEntity<>(new RestResponseMessage<>(true, "아티클 조회 성공", responseDto), HttpStatus.OK);
    }

    // ✅ 아티클 생성
    @ApiOperation(value = "아티클 생성", notes = "아티클 생성")
    @PostMapping("/articles")
    public ResponseEntity<RestResponseMessage<?>> createArticles(@Valid @RequestBody ArticleCreateRequestDto requestDto,
                                                         @AuthenticationPrincipal Member member) {
        ArticleCreateResponseDto responseDto = articleService.createArticle(requestDto, member);
        return new ResponseEntity<>(new RestResponseMessage<>(true, "아티클 생성 성공", responseDto), HttpStatus.OK);
    }

    // 아티클 수정 페이지
    @ApiOperation(value = "아티클 수정", notes = "아티클 수정 API")
    @PatchMapping("/articles/{id}")
    public ResponseEntity<RestResponseMessage<?>> updateArticles(@Valid @RequestBody ArticleUpdateRequestDto requestDto,
                                                         @PathVariable long id,
                                                         @AuthenticationPrincipal Member member) {
        articleService.updateArticle(requestDto, id, member);
        return new ResponseEntity<>(new RestResponseMessage<>(true, "아티클 수정 성공", ""), HttpStatus.OK);
    }

    // 리뷰 수정
    @ApiOperation(value = "리뷰(메모) 수정", notes = "리뷰(메모) 수정")
    @PatchMapping("/articles/review/{id}")
    public ResponseEntity<RestResponseMessage<?>> updateArticleReview(@Valid @RequestBody ArticleReviewRequestDto requestDto,
                                                                      @PathVariable long id,
                                                                      @AuthenticationPrincipal Member member) {
        ArticleReviewResponseDto responseDto = articleService.updateArticleReview(requestDto, id, member);
        return new ResponseEntity<>(new RestResponseMessage<>(true, "아티클 리뷰 수정 성공", responseDto), HttpStatus.OK);
    }

    // 리뷰 가리기
    @ApiOperation(value = "리뷰(메모) 보이기 / 숨기기", notes = "리뷰(메모) 보이기 / 숨기기")
    @PatchMapping("/articles/reviewhide/{id}")
    public ResponseEntity<RestResponseMessage<?>> updateArticleReviewHide(@PathVariable long id,
                                                                          @AuthenticationPrincipal Member member) {
        ArticleReviewHideResponseDto responseDto = articleService.updateArticleReviewHide(id);
        return new ResponseEntity<>(new RestResponseMessage<>(true, "리뷰 가리기 성공", responseDto), HttpStatus.OK);
    }
}
