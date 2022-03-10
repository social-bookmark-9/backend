package com.sparta.backend.oauthDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoUserInfoRequestDto {
    private String id;
    private String email;
}
