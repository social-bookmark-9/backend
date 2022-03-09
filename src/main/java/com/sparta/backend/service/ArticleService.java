package com.sparta.backend.service;

import com.sparta.backend.model.Article;
import com.sparta.backend.model.Member;
import com.sparta.backend.repository.ArticleRepository;
import com.sparta.backend.requestDto.ArticleCreateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    // 아티클 생성
    public void createArticle(ArticleCreateRequestDto requestDto, Member member) {
        Article article = Article.createArticleDtoBuilder()
                .url(requestDto.getUrl())
                .titleOg(requestDto.getTitleOg())
                .imgOg(requestDto.getImgOg())
                .review(requestDto.getReview())
                .reviewHide(requestDto.getReviewHide())
                .readCount(requestDto.getReadCount())
                .hashtag(requestDto.getHashtag())
                .articleFolder(requestDto.getArticleFolder())
                .build();

        articleRepository.save(article);
    }
}
