package com.sparta.backend.service;


import com.sparta.backend.model.Member;

import com.sparta.backend.requestDto.ArticleFolderCreateRequestDto;
import com.sparta.backend.requestDto.ArticleFolderNameUpdateRequestDto;
import com.sparta.backend.responseDto.ArticlesInFolderResponseDto;

import java.util.List;


public interface ArticleFolderService {

    void createArticleFolder(ArticleFolderCreateRequestDto articleFolderRequestDto, Member member);

    void deleteArticleFolder(Long id);

    void updateArticleFolderName(ArticleFolderNameUpdateRequestDto articleFolderNameUpdateRequestDto, Long id);

    List<ArticlesInFolderResponseDto> findArticlesInFolder(Member member, Long id);

    void deleteArticleInArticleFolder(Long folderId, Long articleId);

}
