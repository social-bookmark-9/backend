package com.sparta.backend.responseDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class MainPageResponseDto {
    private List<RecommendedMemberResponseDto> memberList = new ArrayList<>();
    private List<MainAndSearchPageArticleFolderResponseDto> articleFolderList = new ArrayList<>();
    private List<ArticleRandomResponseDto> articleList = new ArrayList<>();
    private List<String> hashtagButton = new ArrayList<>();

    public MainPageResponseDto(List<RecommendedMemberResponseDto> recommendedMemberResponseDtoList,
                               List<MainAndSearchPageArticleFolderResponseDto> mainAndSearchPageArticleFolderResponseDtoList,
                               List<ArticleRandomResponseDto> articleRandomResponseDtoList,
                               List<String> hashtagButton)  {

        this.memberList = recommendedMemberResponseDtoList;
        this.articleFolderList = mainAndSearchPageArticleFolderResponseDtoList;
        this.articleList = articleRandomResponseDtoList;
        this.hashtagButton = hashtagButton;
    }

    public static MainPageResponseDto of(List<RecommendedMemberResponseDto> recommendedMemberResponseDtoList,
                                      List<MainAndSearchPageArticleFolderResponseDto> mainAndSearchPageArticleFolderResponseDtoList,
                                      List<ArticleRandomResponseDto> articleRandomResponseDtoList,
                                      List<String> hashtagButton) {

        return new MainPageResponseDto(recommendedMemberResponseDtoList, mainAndSearchPageArticleFolderResponseDtoList, articleRandomResponseDtoList, hashtagButton);
    }
}
