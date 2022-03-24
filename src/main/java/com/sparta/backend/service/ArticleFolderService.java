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

    void deleteArticleFolder(long id);

    void updateArticleFolderName(ArticleFolderNameUpdateRequestDto articleFolderNameUpdateRequestDto, long id);

    ArticlesInFolderResponseDto findArticlesInFolder(Member member, long id);

    void deleteArticleInArticleFolder(long folderId, long articleId);

    LikeAddOrRemoveResponseDto likeAddOrRemove(Member member, long folderId);

    List<ArticleFolderNameAndIdResponseDto> getArticleFoldersName(Member member);
}
