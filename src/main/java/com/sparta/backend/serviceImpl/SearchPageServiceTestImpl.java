package com.sparta.backend.serviceImpl;

import com.sparta.backend.model.Article;
import com.sparta.backend.repository.ArticleRepository;
import com.sparta.backend.responseDto.ArticleRandomResponseDto;
import com.sparta.backend.service.SearchPageServiceTest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Transactional
@Service
public class SearchPageServiceTestImpl implements SearchPageServiceTest {

    private final ArticleRepository articleRepository;

    // 검색페이지 아티클 검색 기능
    @Override
    public Map<String, Object> getSearchArticles(String hashtag, String titleOg, Pageable pageable) {

        // 검색어에 따라서 조건
        Page<Article> articles;
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
        List<ArticleRandomResponseDto> articleList = new ArrayList<>();
        for (Article article : articles) {
            ArticleRandomResponseDto responseDto = ArticleRandomResponseDto.builder()
                    .articleId(article.getId())
                    .titleOg(article.getTitleOg())
                    .imgOg(article.getImgOg())
                    .contentOg(article.getContentOg())
                    .hashtag1(article.getHashtag().getHashtag1())
                    .hashtag2(article.getHashtag().getHashtag2())
                    .hashtag3(article.getHashtag().getHashtag3())
                    .build();
            articleList.add(responseDto);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("isFirst", isFirst);
        map.put("isLast", isLast);
        map.put("articleList", articleList);

        return map;
    }

}
