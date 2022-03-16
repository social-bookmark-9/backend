package com.sparta.backend.controller;

import com.sparta.backend.message.RestResponseMessage;
import com.sparta.backend.model.Member;
import com.sparta.backend.requestDto.ArticleCreateRequestDto;
import com.sparta.backend.requestDto.ArticleReviewRequestDto;
import com.sparta.backend.requestDto.ArticleUpdateRequestDto;
import com.sparta.backend.requestDto.ReminderRequestDto;
import com.sparta.backend.responseDto.ArticleResponseDto;
import com.sparta.backend.responseDto.ArticleReviewHideResponseDto;
import com.sparta.backend.responseDto.ArticleReviewResponseDto;
import com.sparta.backend.service.ArticleService;
import com.sparta.backend.service.ReminderService;
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
    private final ReminderService reminderService;

    // ⚠️ 아티클 상세 페이지
    // TODO: 함께보면 좋은글
    // TODO: 리마인드 수정 OR 가져오기
    @ApiOperation(value = "특정 아티클 조회", notes = "특정 아티클 조회 API")
    @GetMapping("/articles/{id}")
    public ResponseEntity<RestResponseMessage<?>> getArticle(@Valid @PathVariable long id,
                                                                   @AuthenticationPrincipal Member member) {
        ArticleResponseDto responseDto = articleService.getArticle(id, member);
        return new ResponseEntity<>(new RestResponseMessage<>(true, "아티클 조회 성공", responseDto), HttpStatus.OK);
    }

    // ✅ 아티클 생성 페이지
    // TODO: Test, 로직 refactor
    @ApiOperation(value = "아티클 생성", notes = "아티클 생성 API")
    @PostMapping("/articles")
    public ResponseEntity<RestResponseMessage<?>> createArticles(@Valid @RequestBody ArticleCreateRequestDto requestDto,
                                                         @AuthenticationPrincipal Member member) {
        articleService.createArticle(requestDto, member);

//        if (requestDto.getButtonDate() != 0) {
//            ReminderRequestDto requestDto1 = ReminderRequestDto.builder()
//                    .titleOg(requestDto.getTitleOg())
//                    .buttonDate(requestDto.getButtonDate())
//                    .url(requestDto.getUrl())
//                    .build();
//            reminderService.createReminder(requestDto1, member);
//        }
        return new ResponseEntity<>(new RestResponseMessage<>(true, "아티클 추가 성공", ""), HttpStatus.OK);
    }

    // ✅ 아티클 수정 페이지
    @ApiOperation(value = "아티클 수정", notes = "아티클 수정 API")
    @PatchMapping("/articles/{id}")
    public ResponseEntity<RestResponseMessage<?>> updateArticles(@Valid @RequestBody ArticleUpdateRequestDto requestDto,
                                                         @PathVariable long id,
                                                         @AuthenticationPrincipal Member member) {
        articleService.updateArticle(requestDto, id, member);
        return new ResponseEntity<>(new RestResponseMessage<>(true, "아티클 수정 성공", ""), HttpStatus.OK);
    }

    // ✅ 리뷰 수정
    @ApiOperation(value = "리뷰(메모) 수정", notes = "리뷰(메모) 수정")
    @PatchMapping("/articles/review/{id}")
    public ResponseEntity<RestResponseMessage<?>> updateArticleReview(@Valid @RequestBody ArticleReviewRequestDto requestDto,
                                                                      @PathVariable long id,
                                                                      @AuthenticationPrincipal Member member) {
        ArticleReviewResponseDto responseDto = articleService.updateArticleReview(requestDto, id, member);
        return new ResponseEntity<>(new RestResponseMessage<>(true, "아티클 리뷰 수정 성공", responseDto), HttpStatus.OK);
    }

    // ✅ 리뷰 가리기
    @ApiOperation(value = "리뷰(메모) 보이기 / 숨기기", notes = "리뷰(메모) 보이기 / 숨기기")
    @PatchMapping("/articles/reviewhide/{id}")
    public ResponseEntity<RestResponseMessage<?>> updateArticleReviewHide(@PathVariable long id,
                                                                          @AuthenticationPrincipal Member member) {
        ArticleReviewHideResponseDto responseDto = articleService.updateArticleReviewHide(id);
        return new ResponseEntity<>(new RestResponseMessage<>(true, "리뷰 가리기 성공", responseDto), HttpStatus.OK);
    }
}
