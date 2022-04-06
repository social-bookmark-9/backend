package com.sparta.backend.serviceImpl;

import com.nimbusds.oauth2.sdk.util.CollectionUtils;
import com.sparta.backend.model.Article;
import com.sparta.backend.model.ArticleFolder;
import com.sparta.backend.repository.ArticleFolderRepository;
import com.sparta.backend.repository.ArticleRepository;
import com.sparta.backend.responseDto.ArticleFolderListResponseDto;
import com.sparta.backend.responseDto.SearchPageArticleResponseDto;
import com.sparta.backend.service.SearchPageServiceTest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class SearchPageServiceTestImpl implements SearchPageServiceTest {

    private final ArticleRepository articleRepository;
    private final ArticleFolderRepository articleFolderRepository;

    // 검색페이지 아티클 검색 기능
    @Override
    public Map<String, Object> getSearchArticles(String hashtag, String titleOg, Pageable pageable) {

        // 검색어에 따라서 조건
        Slice<Article> articles;
        if (Objects.equals(titleOg, "") && Objects.equals(hashtag, "")) {
            articles = articleRepository
                    .findArticlesByArticleFolder_FolderHide(false, pageable);
        } else if (Objects.equals(titleOg, "")) {
            articles = articleRepository
                    .findArticlesByHashtag_Hashtag1AndArticleFolder_FolderHide(hashtag, false, pageable);
        } else if (Objects.equals(hashtag, "")) {
            articles = articleRepository
                    .findArticlesByArticleFolder_FolderHideAndTitleOgContains(false, titleOg, pageable);
        } else {
            articles = articleRepository
                    .findArticlesByHashtag_Hashtag1AndArticleFolder_FolderHideAndTitleOgContains(hashtag, false, titleOg, pageable);
        }

        // 첫페이지 마지막페이지 확인
        boolean isFirst = articles.isFirst();
        boolean isLast = articles.isLast();

        // DTO 변환
        Slice<SearchPageArticleResponseDto> articleList = articles.map(SearchPageArticleResponseDto::new);

        Map<String, Object> map = new HashMap<>();
        map.put("isFirst", isFirst);
        map.put("isLast", isLast);
        map.put("articleList", articleList);

        return map;
    }

    // 검색페이지 아티클 폴더 검색 기능
    @Override
    public Map<String, Object> getSearchArticleFolders(String hashtag, String articleFolderName, Pageable pageable) {

        // 검색어에 따라서 조건
        Page<ArticleFolder> articleFolders;
        if (Objects.equals(articleFolderName, "") && Objects.equals(hashtag, "")) {
            articleFolders = articleFolderRepository
                    .findArticleFoldersByFolderHide(false, pageable);
        } else if (Objects.equals(articleFolderName, "")) {
            articleFolders = articleFolderRepository
                    .findArticleFoldersByFolderHideAndFolderHashtag1(false, hashtag, pageable);
        } else if (Objects.equals(hashtag, "")) {
            articleFolders = articleFolderRepository
                    .findArticleFoldersByFolderHideAndArticleFolderNameContains(false, articleFolderName, pageable);
        } else {
            articleFolders = articleFolderRepository
                    .findArticleFoldersByFolderHideAndFolderHashtag1AndArticleFolderNameContains(false, hashtag, articleFolderName, pageable);
        }

        // 첫페이지 마지막페이지 확인
        boolean isFirst = articleFolders.isFirst();
        boolean isLast = articleFolders.isLast();

        // DTO 변환
        List<ArticleFolderListResponseDto> articleFolderList = new ArrayList<>();
        for (ArticleFolder articleFolder : articleFolders) {
            ArticleFolderListResponseDto articleFolderListResponseDto;
            if (CollectionUtils.isEmpty(articleFolder.getArticles())) {
                articleFolderListResponseDto = ArticleFolderListResponseDto.of(articleFolder);
            } else {
                articleFolderListResponseDto = ArticleFolderListResponseDto.of(articleFolder, articleFolder.getArticles());
            }
            articleFolderList.add(articleFolderListResponseDto);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("isFirst", isFirst);
        map.put("isLast", isLast);
        map.put("articleList", articleFolderList);

        return map;
    }
}
