package com.sparta.backend.controller;

import com.sparta.backend.exception.EntityNotFoundException;
import com.sparta.backend.message.RestResponseMessage;
import com.sparta.backend.model.ArticleFolder;
import com.sparta.backend.model.Member;
import com.sparta.backend.repository.ArticleFolderRepository;
import com.sparta.backend.responseDto.ArticleFolderListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MainPageArticleFolderController {

    private final ArticleFolderRepository articleFolderRepository;

    @GetMapping("/api/mainpage/articlefolders")
    public ResponseEntity<RestResponseMessage> getArticleFolders() {

        List<ArticleFolder> articleFolders = articleFolderRepository.findAll();
        List<ArticleFolderListResponseDto> articleFolderList = new ArrayList<>();
        for (ArticleFolder articleFolder : articleFolders) {
            ArticleFolderListResponseDto articleFolderListResponseDto;
            if (articleFolder.getArticles().isEmpty()) {
                articleFolderListResponseDto = ArticleFolderListResponseDto.of(articleFolder);
            } else {
                articleFolderListResponseDto = ArticleFolderListResponseDto.of(articleFolder, articleFolder.getArticles());
            }
            articleFolderList.add(articleFolderListResponseDto);
        }

        return new ResponseEntity<>(new RestResponseMessage<>(true,"추천 큐레이션 검색 결과", articleFolderList), HttpStatus.OK);
    }
}
