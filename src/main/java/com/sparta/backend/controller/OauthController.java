package com.sparta.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.backend.jwt.JwtTokenProvider;
import com.sparta.backend.message.DataMessage;
import com.sparta.backend.oauthDto.KakaoUserInfoRequestDto;
import com.sparta.backend.oauthDto.KakaoUserInfoResponseDto;
import com.sparta.backend.service.OauthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OauthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final OauthService oauthService;
    
    // 카카오 로그인 실행
//    @GetMapping("/user/kakao/callback")
//    public ResponseEntity<DataMessage> kakaoLogin(@RequestParam String code) throws JsonProcessingException {
//        // 인가 코드 발행, 토큰 발행 및 API 호출
//        KakaoUserInfoRequestDto kakaoUserInfoRequestDto = oauthService.getKakaoInfo(code);
//        // 호출한 정보로 회원가입 여부 판별 및 필요 정보 확인
//        KakaoUserInfoResponseDto kakaoUserInfoResponseDto = oauthService.ifNeededCreateKakaoMemberAndLogin(kakaoUserInfoRequestDto);
//        // 토큰 발급 및 필요 정보 보내기.
//        String token = jwtTokenProvider.createToken(kakaoUserInfoResponseDto.getMember().getUsername(), kakaoUserInfoResponseDto.getMember().getMemberRoles());
//
//        return new ResponseEntity<>(new DataMessage("로그인 성공", token), HttpStatus.OK);
//    }

    // 카카오 로그인 실행
    @GetMapping("/user/kakao/callback")
    public ResponseEntity<DataMessage> kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        // 인가 코드 발행, 토큰 발행 및 API 호출
        KakaoUserInfoRequestDto kakaoUserInfoRequestDto = oauthService.getKakaoInfo(code);
        // 호출한 정보로 회원가입 여부 판별
        boolean isExist = oauthService.checkIfMemberExists(kakaoUserInfoRequestDto);

        if (isExist) {
            KakaoUserInfoResponseDto kakaoUserInfoResponseDto = oauthService.ifNeededCreateKakaoMemberAndLogin(kakaoUserInfoRequestDto);
            String token = jwtTokenProvider.createToken(kakaoUserInfoResponseDto.getMember().getUsername(), kakaoUserInfoResponseDto.getMember().getMemberRoles());
            return new ResponseEntity<>(new DataMessage<>("로그인 성공", token), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new DataMessage<>("아직 회원가입을 하지 않았습니다.", kakaoUserInfoRequestDto), HttpStatus.OK);
        }
    }
}
