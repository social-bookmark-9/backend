package com.sparta.backend.controller;

import com.sparta.backend.message.RestResponseMessage;
import com.sparta.backend.model.Member;
import com.sparta.backend.responseDto.*;
import com.sparta.backend.service.MainPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class MainPageController {

    private final MainPageService mainPageService;

    @GetMapping("/api/mainpage")
    public ResponseEntity<RestResponseMessage> getMainPage(@AuthenticationPrincipal Member getMember) {
        MainPageResponseDto mainPageResponseDto;
        if (getMember == null) {
            mainPageResponseDto = mainPageService.mainPageNonLoginVer();
        } else {
            mainPageResponseDto = mainPageService.mainPageLoginVer(getMember);
        }
        return new ResponseEntity<>(new RestResponseMessage<>(true,"메인페이지 정보 불러오기", mainPageResponseDto), HttpStatus.OK);
    }
}
