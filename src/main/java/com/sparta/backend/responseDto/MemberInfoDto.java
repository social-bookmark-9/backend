package com.sparta.backend.responseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
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

    @Builder
    public MemberInfoDto(long memberId, String memberName, String email, String profileImage, String memberComment,
                         String instagramUrl, String githubUrl, String brunchUrl, String blogUrl, String websiteUrl) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.email = email;
        this.profileImage = profileImage;
        this.memberComment = memberComment;
        this.instagramUrl = instagramUrl;
        this.githubUrl = githubUrl;
        this.brunchUrl = brunchUrl;
        this.blogUrl = blogUrl;
        this.websiteUrl = websiteUrl;
    }
}
