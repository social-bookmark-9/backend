package com.sparta.backend.service;


import com.sparta.backend.model.Member;

import com.sparta.backend.requestDto.ArticleFolderCreateRequestDto;
import com.sparta.backend.requestDto.ArticleFolderNameUpdateRequestDto;
import com.sparta.backend.responseDto.ArticlesInFolderResponseDto;

import java.util.List;


public interface ArticleFolderService {

    void createArticleFolder(ArticleFolderCreateRequestDto articleFolderRequestDto, Member member);

    void deleteArticleFolder(long id);

    void updateArticleFolderName(ArticleFolderNameUpdateRequestDto articleFolderNameUpdateRequestDto, long id);

    List<ArticlesInFolderResponseDto> findArticlesInFolder(Member member, long id);

    void deleteArticleInArticleFolder(long folderId, long articleId);

    void likeAddOrRemove(Member member, long id);
}
