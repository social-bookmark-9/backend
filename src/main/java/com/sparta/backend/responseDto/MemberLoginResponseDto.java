package com.sparta.backend.responseDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberLoginResponseDto {

    private final Long memberId;
    private final String nickName;
    private final String email;
    private final String profileImageUrl;
    private final String userDesc;
    private final String instagramUrl;
    private final String githubUrl;
    private final String brunchUrl;
    private final String blogUrl;
    private final String websiteUrl;

}
