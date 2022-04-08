package com.sparta.backend.responseDto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberHashtagInfoDto {

    private String memberHashtag1;
    private String memberHashtag2;
    private String memberHashtag3;

    @QueryProjection
    public MemberHashtagInfoDto(String memberHashtag1, String memberHashtag2, String memberHashtag3) {
        this.memberHashtag1 = memberHashtag1;
        this.memberHashtag2 = memberHashtag2;
        this.memberHashtag3 = memberHashtag3;
    }
}
