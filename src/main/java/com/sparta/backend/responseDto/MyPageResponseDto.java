package com.sparta.backend.responseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MyPageResponseDto {

    private MemberInfoDto memberInfoDto;
    private ArticleFolderListDto articleFolderListDto;

    @Builder
    public MyPageResponseDto(MemberInfoDto memberInfoDto, ArticleFolderListDto articleFolderListDto) {
        this.memberInfoDto = memberInfoDto;
        this.articleFolderListDto = articleFolderListDto;
    }
}
