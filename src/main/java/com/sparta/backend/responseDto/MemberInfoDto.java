package com.sparta.backend.responseDto;

import com.sparta.backend.model.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberInfoDto {

    private long memberId;
    private String memberName;
    private String email;
    private String profileImage;
    private String memberComment;
    private String instagramUrl;
    private String githubUrl;
    private String brunchUrl;
    private String blogUrl;
    private String websiteUrl;

    private MemberInfoDto(Member member) {
        this.memberId = member.getId();
        this.memberName = member.getMemberName();
        this.email = member.getEmail();
        this.profileImage = member.getProfileImage();
        this.memberComment = member.getMemberComment();
        this.instagramUrl = member.getInstagramUrl();
        this.githubUrl = member.getGithubUrl();
        this.brunchUrl = member.getBrunchUrl();
        this.blogUrl = member.getBlogUrl();
        this.websiteUrl = member.getWebsiteUrl();
    }

    public static MemberInfoDto of(Member member) {
        return new MemberInfoDto(member);
    }
}
