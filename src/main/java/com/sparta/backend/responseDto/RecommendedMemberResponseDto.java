package com.sparta.backend.responseDto;

import lombok.Builder;
import lombok.Getter;

import java.util.Arrays;

@Getter
@Builder
public class RecommendedMemberResponseDto {
    private Long memberId;
    private String profileImage;
    private String memberComment;
    private String memberName;
    private int likeCount;
}
