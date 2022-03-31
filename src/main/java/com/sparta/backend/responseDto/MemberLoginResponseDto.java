package com.sparta.backend.responseDto;

import com.sparta.backend.model.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
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

    @Builder
    public MemberLoginResponseDto(Member member) {
        this.memberId = member.getId();
        this.nickName = member.getMemberName();
        this.email = member.getEmail();
        this.profileImageUrl = member.getProfileImage();
        this.userDesc = member.getMemberComment();
        this.instagramUrl = member.getInstagramUrl();
        this.githubUrl = member.getGithubUrl();
        this.brunchUrl = member.getBrunchUrl();
        this.blogUrl = member.getBlogUrl();
        this.websiteUrl = member.getWebsiteUrl();
    }
}
