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
    private String folderHashtag1;
    private String folderHashtag2;
    private String folderHashtag3;

    @QueryProjection
    public MemberHashtagInfoDto(String memberHashtag1, String memberHashtag2, String memberHashtag3, String folderHashtag1, String folderHashtag2, String folderHashtag3) {
        this.memberHashtag1 = memberHashtag1;
        this.memberHashtag2 = memberHashtag2;
        this.memberHashtag3 = memberHashtag3;
        this.folderHashtag1 = folderHashtag1;
        this.folderHashtag2 = folderHashtag2;
        this.folderHashtag3 = folderHashtag3;
    }
}
