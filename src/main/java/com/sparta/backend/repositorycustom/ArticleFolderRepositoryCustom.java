package com.sparta.backend.repositorycustom;

import com.sparta.backend.model.ArticleFolder;
import com.sparta.backend.model.Member;
import com.sparta.backend.responseDto.MainPageArticleFolderResponseDto;

import java.util.List;

public interface ArticleFolderRepositoryCustom {
    List<ArticleFolder> myPageArticleFolder(Member member);
    List<MainPageArticleFolderResponseDto> mainPageArticleFolderLogin(Long memberId, List<String> hashTagList);
    List<MainPageArticleFolderResponseDto> mainPageArticleFolderNonLogin();
    List<String> articleFolderHashtagInfo(Long memberId);
}
