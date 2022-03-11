package com.sparta.backend.oauthDto;

import com.sparta.backend.model.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KakaoMemberInfoResponseDto {
    private Member member;
}
