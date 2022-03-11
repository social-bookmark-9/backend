package com.sparta.backend.service;


import com.sparta.backend.model.Member;

import com.sparta.backend.requestDto.CreateArticleFolderReqDto;
import com.sparta.backend.requestDto.UpdateAFNameReqDto;
import com.sparta.backend.responseDto.ArticlesInFolderRespDto;

import java.util.List;


public interface ArticleFolderService {

    void createArticleFolder(CreateArticleFolderReqDto articleFolderRequestDto, Member member);

    void deleteArticleFolder(Long id);

    void updateArticleFolderName(UpdateAFNameReqDto updateAFNameReqDto, Long id);

    List<ArticlesInFolderRespDto> findArticlesInFolder(Long id);

}
