package com.sparta.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.backend.message.RestResponseMessage;
import com.sparta.backend.model.Member;
import com.sparta.backend.oauthDto.KakaoMemberInfoRequestDto;
import com.sparta.backend.oauthDto.KakaoMemberRegisterRequestDto;
import com.sparta.backend.oauthDto.TokenDto;
import com.sparta.backend.oauthDto.TokenRequestDto;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;

public interface OauthService {

    KakaoMemberInfoRequestDto getKakaoInfo(String code) throws JsonProcessingException;

    Member createKakaoMember(KakaoMemberRegisterRequestDto kakaoMemberRegisterRequestDto);

    TokenDto reissue(TokenRequestDto tokenRequestDto);

    void LoginCheckRefreshToken(Member member, TokenDto token);

    void saveRefreshToken(Member member, TokenDto tokenDto);

    void deleteRefreshToken(String refreshToken);

    ResponseEntity<RestResponseMessage> checkRegister(KakaoMemberInfoRequestDto kakaoMemberInfoRequestDto, HttpServletResponse response);
}

