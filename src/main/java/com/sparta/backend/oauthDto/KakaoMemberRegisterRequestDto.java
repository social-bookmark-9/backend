package com.sparta.backend.oauthDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KakaoMemberRegisterRequestDto {

    private String kakaoId;

    private String email;

    private String memberName;

    private String hashtag1;

    private String hashtag2;

    private String hashtag3;

}
