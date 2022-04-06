package com.sparta.backend.controller;

import com.sparta.backend.exception.ArticleAccessDeniedException;
import com.sparta.backend.exception.ErrorCode;
import com.sparta.backend.message.RestResponseMessage;
import com.sparta.backend.model.Member;
import com.sparta.backend.requestDto.*;
import com.sparta.backend.responseDto.*;
import com.sparta.backend.service.ArticleService;
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

    public void isLoggedIn(Member member) {
        if (member == null) { throw new ArticleAccessDeniedException(ErrorCode.HANDLE_ACCESS_DENIED); }}

    // 아티클 생성 ✅
    @PostMapping("/articles")
    public ResponseEntity<RestResponseMessage<?>> createArticle(@Valid @RequestBody ArticleCreateRequestDto requestDto,
                                                                @AuthenticationPrincipal Member member) {
        isLoggedIn(member);
        ArticleCreateResponseDto responseDto = articleService.createArticle(requestDto, member);
        return new ResponseEntity<>(new RestResponseMessage<>(true, "아티클 생성 성공", responseDto), HttpStatus.OK);
    }

    // 특정 아티클 조회 ✅
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
    @DeleteMapping("/articles/{id}")
    public ResponseEntity<RestResponseMessage<?>> deleteArticle(@PathVariable Long id,
                                                                @AuthenticationPrincipal Member member) {
        isLoggedIn(member);
        articleService.deleteArticle(id, member);
        return new ResponseEntity<>(new RestResponseMessage<>(true, "아티클 삭제 성공", ""), HttpStatus.OK);
    }

    // 아티클 제목 수정 ✅
    @PatchMapping("/articles/{id}/title")
    public ResponseEntity<RestResponseMessage<?>> updateArticleTitle(@Valid @RequestBody ArticleTitleRequestDto requestDto,
                                                                     @PathVariable Long id,
                                                                     @AuthenticationPrincipal Member member) {
        isLoggedIn(member);
        ArticleTitleResponseDto responseDto = articleService.updateArticleTitle(requestDto, id, member);
        return new ResponseEntity<>(new RestResponseMessage<>(true, "아티클 제목 수정 성공", responseDto), HttpStatus.OK);
    }

    // 아티클 해시태그 수정 ✅
    @PatchMapping("/articles/{id}/hashtag")
    public ResponseEntity<RestResponseMessage<?>> updateArticleHashtag(@Valid @RequestBody HashtagUpdateRequestDto requestDto,
                                                                       @PathVariable Long id,
                                                                       @AuthenticationPrincipal Member member) {
        isLoggedIn(member);
        articleService.updateArticleHashtag(requestDto, id, member);
        return new ResponseEntity<>(new RestResponseMessage<>(true, "아티클 해시태그 수정 성공", ""), HttpStatus.OK);
    }

    // 아티클 리뷰 수정 ✅
    @PatchMapping("/articles/{id}/review")
    public ResponseEntity<RestResponseMessage<?>> updateArticleReview(@Valid @RequestBody ArticleReviewRequestDto requestDto,
                                                                      @PathVariable Long id,
                                                                      @AuthenticationPrincipal Member member) {
        isLoggedIn(member);
        ArticleReviewResponseDto responseDto = articleService.updateArticleReview(requestDto, id, member);
        return new ResponseEntity<>(new RestResponseMessage<>(true, "아티클 리뷰 수정 성공", responseDto), HttpStatus.OK);
    }

    // 아티클 리뷰 공개여부 수정 ✅
    @PatchMapping("/articles/{id}/review/hide")
    public ResponseEntity<RestResponseMessage<?>> updateArticleReviewHide(@PathVariable Long id,
                                                                          @AuthenticationPrincipal Member member) {
        isLoggedIn(member);
        ArticleReviewHideResponseDto responseDto = articleService.updateArticleReviewHide(id, member);
        return new ResponseEntity<>(new RestResponseMessage<>(true, "리뷰 가리기 성공", responseDto), HttpStatus.OK);
    }

    // 아티클 모든 리뷰 조회 ✅
    @GetMapping("/reviews")
    public ResponseEntity<RestResponseMessage<?>> getArticleReviews(@AuthenticationPrincipal Member member) {
        isLoggedIn(member);
        ArticleReviewResponseDtos responseDtos = articleService.getArticleReviews(member);
        return new ResponseEntity<>(new RestResponseMessage<>(true, "모든 리뷰 가져오기 성공", responseDtos), HttpStatus.OK);
    }

    // 아티클 읽은 횟수 증가 ✅
    @PatchMapping("/articles/{id}/readcount")
    public ResponseEntity<RestResponseMessage<?>> addArticleReadCount(@PathVariable Long id,
                                                                      @AuthenticationPrincipal Member member) {
        isLoggedIn(member);
        articleService.addArticleReadCount(id, member);
        return new ResponseEntity<>(new RestResponseMessage<>(true, "아티클 읽은 횟수 증가 성공", ""), HttpStatus.OK);
    }

    // 아티클의 폴더 이동 ✅
    @PatchMapping("/articles/{id}/folder")
    public ResponseEntity<RestResponseMessage<?>> updateArticleFolderChange(@Valid @RequestBody ArticleFolderChangeUpdateRequestDto requestDto,
                                                                            @PathVariable Long id,
                                                                            @AuthenticationPrincipal Member member) {
        isLoggedIn(member);
        articleService.updateArticleFolderChange(requestDto, id, member);
        return new ResponseEntity<>(new RestResponseMessage<>(true, "아티클의 폴더 이동 성공", ""), HttpStatus.OK);
    }

    // 타유저 아티클 모두 저장
    @PatchMapping("/articles/articlefolder/{id}")
    public ResponseEntity<RestResponseMessage<?>> saveAllArticlesByOtherUser(@Valid @RequestBody ArticleFolderChangeUpdateRequestDto requestDto,
                                                                             @PathVariable Long id,
                                                                             @AuthenticationPrincipal Member member) {
        isLoggedIn(member);
        articleService.saveAllArticlesByOtherUser(requestDto, id, member);
        return new ResponseEntity<>(new RestResponseMessage<>(true, "타유저 아티클 모두 저장 성공", ""), HttpStatus.OK);
    }
}