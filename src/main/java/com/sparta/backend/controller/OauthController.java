package com.sparta.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.backend.oauthDto.KakaoUserInfoDto;
import com.sparta.backend.service.OauthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OauthController {

    private final OauthService oauthService;
    
    // 인가 코드 받아오기
    @GetMapping("/user/kakao/callback")
    public KakaoUserInfoDto kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        KakaoUserInfoDto kakaoUserInfoDto = oauthService.kakaoLogin(code);
        return kakaoUserInfoDto;
    }
}
