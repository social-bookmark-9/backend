package com.sparta.backend.oauthDto;

import com.sparta.backend.model.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoUserInfoResponseDto {
    private Member member;
}
