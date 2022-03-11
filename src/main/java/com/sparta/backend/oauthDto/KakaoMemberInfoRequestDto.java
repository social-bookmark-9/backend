package com.sparta.backend.oauthDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoMemberInfoRequestDto {

    private String kakaoId;

    private String email;

    private String profileImage;
}
