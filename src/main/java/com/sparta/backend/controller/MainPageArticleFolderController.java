package com.sparta.backend.controller;

import com.sparta.backend.message.RestResponseMessage;
import com.sparta.backend.model.Member;
import com.sparta.backend.responseDto.ArticleFolderListResponseDto;
import com.sparta.backend.service.MainPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class MainPageArticleFolderController {

    private final MainPageService mainPageService;

    @GetMapping("/api/mainpage/articlefolders")
    public ResponseEntity<RestResponseMessage> getArticleFolders(@AuthenticationPrincipal Member getMember) {

        List<ArticleFolderListResponseDto> articleFolderList = mainPageService.getRecommendedArticleFolders(getMember);

        return new ResponseEntity<>(new RestResponseMessage<>(true,"추천 큐레이션 검색 결과", articleFolderList), HttpStatus.OK);
    }
}
