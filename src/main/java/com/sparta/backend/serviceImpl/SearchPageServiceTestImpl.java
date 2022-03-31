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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Transactional
@Service
public class SearchPageServiceTestImpl implements SearchPageServiceTest {

    private final ArticleRepository articleRepository;

    // 검색페이지 아티클 검색 기능
   @Override
    public List<ArticleRandomResponseDto> getSearchArticles(String hashtag, String titleOg, Pageable pageable) {

        Page<Article> articles;
        if (Objects.equals(titleOg, "") && Objects.equals(hashtag, "")) {
            articles = articleRepository
                    .findArticlesByArticleFolder_FolderHide(false, pageable);
        }
        else if (Objects.equals(titleOg, "")) {
            articles = articleRepository
                    .findArticlesByHashtag_Hashtag1AndArticleFolder_FolderHide(hashtag, false, pageable);
        }
        else if (Objects.equals(hashtag, "")) {
            articles = articleRepository
                    .findArticlesByArticleFolder_FolderHideAndTitleOgContains(false, titleOg, pageable);
        }
        else {
            articles = articleRepository
                    .findArticlesByHashtag_Hashtag1AndArticleFolder_FolderHideAndTitleOgContains(hashtag, false, titleOg, pageable);
        }

        List<ArticleRandomResponseDto> articleList = new ArrayList<>();
        for(Article article : articles) {
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
        return articleList;
    }

}
