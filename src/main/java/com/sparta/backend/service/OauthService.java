package com.sparta.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sparta.backend.model.Member;
import com.sparta.backend.oauthDto.KakaoMemberInfoRequestDto;
import com.sparta.backend.oauthDto.KakaoMemberRegisterRequestDto;
import com.sparta.backend.oauthDto.TokenDto;
import com.sparta.backend.oauthDto.TokenRequestDto;

public interface OauthService {

    KakaoMemberInfoRequestDto getKakaoInfo(String code) throws JsonProcessingException;

    boolean checkIfMemberExists(KakaoMemberInfoRequestDto kakaoUserInfo);

    Member createKakaoMember(KakaoMemberRegisterRequestDto kakaoMemberRegisterRequestDto);

    TokenDto reissue(TokenRequestDto tokenRequestDto);

    void deleteRefreshToken(String refreshToken);
}

