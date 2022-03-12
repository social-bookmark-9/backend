package com.sparta.backend.serviceImpl;

import com.sparta.backend.model.Article;
import com.sparta.backend.model.ArticleFolder;
import com.sparta.backend.model.Member;
import com.sparta.backend.repository.ArticleFolderRepository;
import com.sparta.backend.repository.ArticleRepository;
import com.sparta.backend.requestDto.ArticleCreateRequestDto;
import com.sparta.backend.requestDto.ArticleUpdateRequestDto;
import com.sparta.backend.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleFolderRepository articleFolderRepository;

    @Override
    public Article getArticle(Long id) {
        return articleRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    // 아티클 생성
    @Override
    public Long createArticle(ArticleCreateRequestDto requestDto, Member member) {
        Article article = Article.builder()
                .url(requestDto.getUrl())
                .titleOg(requestDto.getTitleOg())
                .imgOg(requestDto.getImgOg())
                .contentOg(requestDto.getContentOg())
                .review(requestDto.getReview())
                .reviewHide(requestDto.getReviewHide())
                .readCount(requestDto.getReadCount())
                .hashtag(requestDto.getHashtag())
                .articleFolder(requestDto.getArticleFolder())
                .build();

        article.setArticleFolder(requestDto.getArticleFolder());

        articleRepository.save(article);
        return article.getId();
    }

    @Override
    public void updateArticle(ArticleUpdateRequestDto requestDto, Member member, Long id) {
        Article currentArticle = articleRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        // TODO: 1. currentArticle 의 currentArticleFolder 를 찾기
        ArticleFolder currentArticleFolder = currentArticle.getArticleFolder();
        String enteredArticleFolderName = requestDto.getArticleFolder().getArticleFolderName();

        // TODO: 2. currentArticleFolder 에서 currentArticle 을 제거
        currentArticleFolder.deleteArticleFromArticleFolder(currentArticle);

        // TODO: 3. 입력받은 ArticleFolderName 으로 ArticleFolder 객체를 찾기
        ArticleFolder toMoveArticleFolder = articleFolderRepository.findArticleFolderByArticleFolderName(enteredArticleFolderName);

        // TODO: 4. 찾은 ArticleFolder 에 currentArticle 을 Add 하기
        toMoveArticleFolder.getArticles().add(currentArticle);

        currentArticle.updateArticle(requestDto);
    }
}
