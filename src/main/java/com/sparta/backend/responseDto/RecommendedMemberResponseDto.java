package com.sparta.backend.responseDto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@Getter
@Builder
@NoArgsConstructor
public class RecommendedMemberResponseDto {
    private Long memberId;
    private String profileImage;
    private String memberComment;
    private String memberName;
    private int likeCount;
    private String hashtag1;
    private String hashtag2;
    private String hashtag3;

    @QueryProjection
    public RecommendedMemberResponseDto(Long memberId, String profileImage, String memberComment, String memberName, int likeCount,
                                        String hashtag1, String hashtag2, String hashtag3) {
        this.memberId = memberId;
        this.profileImage = profileImage;
        this.memberComment = memberComment;
        this.memberName = memberName;
        this.likeCount = likeCount;
        this.hashtag1 = hashtag1;
        this.hashtag2 = hashtag2;
        this.hashtag3 = hashtag3;
    }
}
