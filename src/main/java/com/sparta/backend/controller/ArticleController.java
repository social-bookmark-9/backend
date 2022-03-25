package com.sparta.backend.controller;

import com.sparta.backend.exception.ArticleAccessDeniedException;
import com.sparta.backend.exception.ErrorCode;
import com.sparta.backend.message.RestResponseMessage;
import com.sparta.backend.model.Member;
import com.sparta.backend.requestDto.*;
import com.sparta.backend.responseDto.*;
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

    public void isLoggedIn(Member member) { if (member == null) { throw new ArticleAccessDeniedException(ErrorCode.HANDLE_ACCESS_DENIED); }}

    // 아티클 생성 ✅
    @ApiOperation(value = "아티클 생성", notes = "아티클 생성 API")
    @PostMapping("/articles")
    public ResponseEntity<RestResponseMessage<?>> createArticles(@Valid @RequestBody ArticleCreateRequestDto requestDto,
                                                                 @AuthenticationPrincipal Member member) {
        isLoggedIn(member);
        ArticleCreateResponseDto responseDto = articleService.createArticle(requestDto, member);
        return new ResponseEntity<>(new RestResponseMessage<>(true, "아티클 생성 성공", responseDto), HttpStatus.OK);
    }

    // 특정 아티클 조회 ✅
    @ApiOperation(value = "특정 아티클 조회", notes = "특정 아티클 조회 API")
    @GetMapping("/articles/{id}")
    public ResponseEntity<RestResponseMessage<?>> getArticle(@Valid @PathVariable Long id,
                                                             @AuthenticationPrincipal Member member) {
        if (member == null) {
            ArticleGetResponseDto responseDto = articleService.getArticleForGuest(id);
            return new ResponseEntity<>(new RestResponseMessage<>(true, "아티클 조회 성공(게스트)", responseDto), HttpStatus.OK);
        }
        ArticleGetResponseDto responseDto = articleService.getArticleForMember(id, member);
        return new ResponseEntity<>(new RestResponseMessage<>(true, "아티클 조회 성공(로그인)", responseDto), HttpStatus.OK);
    }

    // 아티클 삭제 ✅
    @ApiOperation(value = "아티클 삭제", notes = "아티클 삭제 API")
    @DeleteMapping("/articles/{id}")
    public ResponseEntity<RestResponseMessage<?>> deleteArticle(@PathVariable Long id,
                                                                @AuthenticationPrincipal Member member) {
        isLoggedIn(member);
        articleService.deleteArticle(id, member);
        return new ResponseEntity<>(new RestResponseMessage<>(true, "아티클 삭제 성공", ""), HttpStatus.OK);
    }

    // 아티클 제목 수정 ✅
    @ApiOperation(value = "아티클 제목 수정", notes = "아티클 제목 수정")
    @PatchMapping("/articles/{id}/title")
    public ResponseEntity<RestResponseMessage<?>> updateTitle(@Valid @RequestBody ArticleTitleRequestDto requestDto,
                                                              @PathVariable Long id,
                                                              @AuthenticationPrincipal Member member) {
        isLoggedIn(member);
        ArticleTitleResponseDto responseDto = articleService.updateTitle(requestDto, id, member);
        return new ResponseEntity<>(new RestResponseMessage<>(true, "아티클 제목 수정 성공", responseDto), HttpStatus.OK);
    }

    // 아티클 해시태그 수정 ✅
    @ApiOperation(value = "아티클 해시태그 수정", notes = "아티클 해시태그 수정 API")
    @PatchMapping("/articles/{id}/hashtag")
    public ResponseEntity<RestResponseMessage<?>> updateHashtag(@Valid @RequestBody HashtagUpdateRequestDto requestDto,
                                                                @PathVariable Long id,
                                                                @AuthenticationPrincipal Member member) {
        isLoggedIn(member);
        articleService.updateHashtag(requestDto, id, member);
        return new ResponseEntity<>(new RestResponseMessage<>(true, "아티클 해시태그 수정 성공", ""), HttpStatus.OK);
    }

    // 아티클 리뷰 수정 ✅
    @ApiOperation(value = "리뷰(메모) 수정", notes = "리뷰(메모) 수정")
    @PatchMapping("/articles/{id}/review")
    public ResponseEntity<RestResponseMessage<?>> updateArticleReview(@Valid @RequestBody ArticleReviewRequestDto requestDto,
                                                                      @PathVariable Long id,
                                                                      @AuthenticationPrincipal Member member) {
        isLoggedIn(member);
        ArticleReviewResponseDto responseDto = articleService.updateArticleReview(requestDto, id, member);
        return new ResponseEntity<>(new RestResponseMessage<>(true, "아티클 리뷰 수정 성공", responseDto), HttpStatus.OK);
    }

    // 아티클 리뷰 공개여부 수정 ✅
    @ApiOperation(value = "리뷰(메모) 보이기 / 숨기기", notes = "리뷰(메모) 보이기 / 숨기기")
    @PatchMapping("/articles/{id}/review/hide")
    public ResponseEntity<RestResponseMessage<?>> updateArticleReviewHide(@PathVariable Long id,
                                                                          @AuthenticationPrincipal Member member) {
        isLoggedIn(member);
        ArticleReviewHideResponseDto responseDto = articleService.updateArticleReviewHide(id, member);
        return new ResponseEntity<>(new RestResponseMessage<>(true, "리뷰 가리기 성공", responseDto), HttpStatus.OK);
    }

    // 아티클 모든 리뷰 조회 ✅
    @ApiOperation(value = "모든 리뷰 가져오기", notes = "모든 리뷰 가져오기 API")
    @GetMapping("/reviews")
    public ResponseEntity<RestResponseMessage<?>> getReviews(@AuthenticationPrincipal Member member) {
        isLoggedIn(member);
        ArticleReviewResponseDtos responseDtos = articleService.getReviews(member);
        return new ResponseEntity<>(new RestResponseMessage<>(true, "모든 리뷰 가져오기 성공", responseDtos), HttpStatus.OK);
    }

    // 아티클 읽은 횟수 증가 ✅
    @ApiOperation(value = "아티클 읽은 횟수 증가", notes = "아티클 읽은 횟수 증가 API")
    @PatchMapping("/articles/{id}/readcount")
    public ResponseEntity<RestResponseMessage<?>> addReadCount(@PathVariable Long id,
                                                               @AuthenticationPrincipal Member member) {
        isLoggedIn(member);
        articleService.addReadCount(id, member);
        return new ResponseEntity<>(new RestResponseMessage<>(true, "아티클 읽은 횟수 증가 성공", ""), HttpStatus.OK);
    }

    // 아티클의 폴더 이동
    @ApiOperation(value = "아티클의 폴더 이동", notes = "아티클의 폴더 이동 API")
    @PatchMapping("/articles/{id}")
    public ResponseEntity<RestResponseMessage<?>> moveMyArticleToAnotherFolder(@Valid @RequestBody ArticleUpdateRequestDto requestDto,
                                                                               @PathVariable Long id,
                                                                               @AuthenticationPrincipal Member member) {
        isLoggedIn(member);
        articleService.moveMyArticleToAnotherFolder(requestDto, id, member);
        return new ResponseEntity<>(new RestResponseMessage<>(true, "아티클 폴더 이동 성공", ""), HttpStatus.OK);
    }
}