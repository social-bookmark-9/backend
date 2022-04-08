package com.sparta.backend.service;


import com.sparta.backend.model.Member;

import com.sparta.backend.requestDto.ArticleFolderCreateRequestDto;
import com.sparta.backend.requestDto.ArticleFolderNameUpdateRequestDto;
import com.sparta.backend.responseDto.ArticleFolderNameAndIdResponseDto;
import com.sparta.backend.responseDto.ArticlesInFolderResponseDto;
import com.sparta.backend.responseDto.LikeAddOrRemoveResponseDto;

import java.util.List;


public interface ArticleFolderService {

    void createArticleFolder(ArticleFolderCreateRequestDto articleFolderRequestDto, Member member);

    void deleteArticleFolder(Member member, Long id);

    void updateArticleFolderName(ArticleFolderNameUpdateRequestDto articleFolderNameUpdateRequestDto, Long id);

    ArticlesInFolderResponseDto findArticlesInFolderLogin(Member member, Long id);

    ArticlesInFolderResponseDto findArticlesInFolderNonLogin(Long id);

    void deleteArticleInArticleFolder(Long folderId, Long articleId);

    LikeAddOrRemoveResponseDto likeAddOrRemove(Member member, Long folderId);

    List<ArticleFolderNameAndIdResponseDto> getArticleFoldersName(Member member);
}
