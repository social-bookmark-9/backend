package com.sparta.backend.repositorycustom;

import com.sparta.backend.model.ArticleFolder;
import com.sparta.backend.model.Member;
import com.sparta.backend.responseDto.MainAndSearchPageArticleFolderResponseDto;

import java.util.List;

public interface ArticleFolderRepositoryCustom {
    List<ArticleFolder> myPageArticleFolder(Member member);
    List<MainAndSearchPageArticleFolderResponseDto> mainPageArticleFolderLogin(Long memberId, List<String> hashTagList);
    List<MainAndSearchPageArticleFolderResponseDto> mainPageArticleFolderNonLogin(String hashtag);
}
