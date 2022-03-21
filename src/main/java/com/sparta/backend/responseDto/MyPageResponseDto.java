package com.sparta.backend.responseDto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyPageResponseDto {

    private MemberInfoDto memberInfoDto;
    private List<ArticleFolderListDto> articleFolderListDto = new ArrayList<>();

    private MyPageResponseDto(MemberInfoDto memberInfoDto, List<ArticleFolderListDto> articleFolderListDto) {
        this.memberInfoDto = memberInfoDto;
        this.articleFolderListDto.addAll(articleFolderListDto);
    }

    public static MyPageResponseDto of(MemberInfoDto memberInfoDto, List<ArticleFolderListDto> articleFolderListDto) {
        return new MyPageResponseDto(memberInfoDto, articleFolderListDto);
    }
}
