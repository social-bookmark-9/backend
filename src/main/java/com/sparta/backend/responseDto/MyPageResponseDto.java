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

    private MemberInfoResponseDto memberInfoResponseDto;
    private List<ArticleFolderListResponseDto> articleFolderListResponseDto = new ArrayList<>();

    private MyPageResponseDto(MemberInfoResponseDto memberInfoResponseDto, List<ArticleFolderListResponseDto> articleFolderListResponseDto) {
        this.memberInfoResponseDto = memberInfoResponseDto;
        this.articleFolderListResponseDto.addAll(articleFolderListResponseDto);
    }

    public static MyPageResponseDto of(MemberInfoResponseDto memberInfoResponseDto, List<ArticleFolderListResponseDto> articleFolderListResponseDto) {
        return new MyPageResponseDto(memberInfoResponseDto, articleFolderListResponseDto);
    }
}
