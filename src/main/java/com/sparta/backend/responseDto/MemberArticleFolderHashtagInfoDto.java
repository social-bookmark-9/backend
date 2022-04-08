package com.sparta.backend.responseDto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberArticleFolderHashtagInfoDto {

    private String folderHashtag1;
    private String folderHashtag2;
    private String folderHashtag3;

    @QueryProjection
    public MemberArticleFolderHashtagInfoDto(String folderHashtag1, String folderHashtag2, String folderHashtag3) {
        this.folderHashtag1 = folderHashtag1;
        this.folderHashtag2 = folderHashtag2;
        this.folderHashtag3 = folderHashtag3;
    }
}
