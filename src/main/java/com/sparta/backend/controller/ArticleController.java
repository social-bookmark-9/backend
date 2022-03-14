package com.sparta.backend.controller;

import com.sparta.backend.message.RestResponseMessage;
import com.sparta.backend.model.Member;
import com.sparta.backend.requestDto.ArticleCreateRequestDto;
import com.sparta.backend.requestDto.ArticleReviewRequestDto;
import com.sparta.backend.requestDto.ArticleUpdateRequestDto;
import com.sparta.backend.responseDto.ArticleResponseDto;
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

    // 아티클 상세 페이지
    // TODO: 함께보면 좋은글
    // TODO: 리마인드 수정 OR 가져오기?
    @ApiOperation(value = "특정 아티클 조회", notes = "특정 아티클 조회 API")
    @GetMapping("/articles/{id}")
    public ResponseEntity<RestResponseMessage<?>> getArticle(@Valid @PathVariable long id,
                                                                   @AuthenticationPrincipal Member member) {
        // TODO: 로그인 유무 체크?
        ArticleResponseDto responseDto = articleService.getArticle(id, member);
        return new ResponseEntity<>(new RestResponseMessage<>(true, "링크를 성공적으로 가져왔습니다.", responseDto), HttpStatus.OK);
    }

    // 아티클 생성 페이지
    // TODO: 리마인더 의논하기
    @ApiOperation(value = "아티클 생성", notes = "아티클 생성 API")
    @PostMapping("/articles")
    public ResponseEntity<RestResponseMessage<?>> createArticles(@Valid @RequestBody ArticleCreateRequestDto requestDto,
                                                         @AuthenticationPrincipal Member member) {
        articleService.createArticle(requestDto, member);
        return new ResponseEntity<>(new RestResponseMessage<>(true, "링크가 추가되었습니다.", ""), HttpStatus.OK);
    }

    // ✅ 아티클 수정 페이지
    @ApiOperation(value = "아티클 수정", notes = "아티클 수정 API")
    @PatchMapping("/articles/{id}")
    public ResponseEntity<RestResponseMessage<?>> updateArticles(@Valid @RequestBody ArticleUpdateRequestDto requestDto,
                                                         @PathVariable long id,
                                                         @AuthenticationPrincipal Member member) {
        articleService.updateArticle(requestDto, id, member);
        return new ResponseEntity<>(new RestResponseMessage<>(true, "링크가 수정되었습니다.", ""), HttpStatus.OK);
    }

    // ✅ 리뷰 수정
    @ApiOperation(value = "리뷰(메모) 수정", notes = "리뷰(메모) 수정")
    @PatchMapping("/articles/review/{id}")
    public ResponseEntity<RestResponseMessage<?>> updateArticleReview(@Valid @RequestBody ArticleReviewRequestDto requestDto,
                                                                      @PathVariable long id,
                                                                      @AuthenticationPrincipal Member member) {
        ArticleReviewResponseDto responseDto = articleService.updateArticleReview(requestDto, id, member);
        return new ResponseEntity<>(new RestResponseMessage<>(true, "메모가 수정되었습니다.", responseDto), HttpStatus.OK);
    }

    // 리뷰 가리기
    @ApiOperation(value = "리뷰(메모) 보이기 / 숨기기", notes = "리뷰(메모) 보이기 / 숨기기")
    @PatchMapping("/articles/reviewHide/{id}")
    public ResponseEntity<RestResponseMessage<?>> updateArticleReviewHide(@PathVariable long id,
                                                                          @AuthenticationPrincipal Member member) {
        boolean reviewHide = articleService.updateArticleReviewHide(id);
        return new ResponseEntity<>(new RestResponseMessage<>(true, "리뷰Hide 수정되었습니다.", reviewHide), HttpStatus.OK);
    }
}
