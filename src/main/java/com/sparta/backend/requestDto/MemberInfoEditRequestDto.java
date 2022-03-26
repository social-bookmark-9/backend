package com.sparta.backend.requestDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberInfoEditRequestDto {

    private String userDesc;

    private String instagramUrl;
    private String githubUrl;
    private String brunchUrl;
    private String blogUrl;
    private String websiteUrl;

    private String profileImageUrl;

    private String nickname;

    private String hashtag1;
    private String hashtag2;
    private String hashtag3;

    private String email;
}
