package com.sparta.backend.serviceImpl;

import com.sparta.backend.model.Article;
import com.sparta.backend.model.ArticleFolder;
import com.sparta.backend.model.Hashtag;
import com.sparta.backend.model.Member;
import com.sparta.backend.repository.ArticleFolderRepository;
import com.sparta.backend.repository.ArticleRepository;
import com.sparta.backend.requestDto.ArticleCreateRequestDto;
import com.sparta.backend.requestDto.ArticleReviewRequestDto;
import com.sparta.backend.requestDto.ArticleUpdateRequestDto;
import com.sparta.backend.responseDto.ArticleResponseDto;
import com.sparta.backend.responseDto.ArticleReviewHideResponseDto;
import com.sparta.backend.responseDto.ArticleReviewResponseDto;
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

    // 특정 아티클 조회
    @Override
    public ArticleResponseDto getArticle(long id, Member member) {
        Article article = articleRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return ArticleResponseDto.builder()
                .hashtag1(article.getHashtag().getHashtag1())
                .hashtag2(article.getHashtag().getHashtag2())
                .hashtag3(article.getHashtag().getHashtag3())
                .titleOg(article.getTitleOg())
                .contentOg(article.getContentOg())
                .review(article.getReview())
                .reviewHide(article.isReviewHide())
                .articleFolder(article.getArticleFolder())
                // TODO: 함께보면 좋은글
                // TODO: 리마인드 Patch or Get?
                .build();
    }

    // 아티클 생성
    @Override
    public long createArticle(ArticleCreateRequestDto requestDto, Member member) {
        Hashtag hashtag = Hashtag.builder()
                .hashtag1(requestDto.getHashtag1())
                .hashtag2(requestDto.getHashtag2())
                .hashtag3(requestDto.getHashtag3())
                .build();

        Article article = Article.builder()
                .url(requestDto.getUrl())
                .titleOg(requestDto.getTitleOg())
                .imgOg(requestDto.getImgOg())
                .contentOg(requestDto.getContentOg())
                .review(requestDto.getReview())
                .reviewHide(requestDto.isReviewHide())
                .readCount(requestDto.getReadCount())
                .hashtag(hashtag)
                .articleFolder(requestDto.getArticleFolder())
                .build();

        article.setArticleFolder(requestDto.getArticleFolder());

        articleRepository.save(article);
        return article.getId();
    }

    // 아티클의 아티클 폴더 변경
    @Override
    public void updateArticle(ArticleUpdateRequestDto requestDto, long id, Member member) {
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

    // 리뷰 수정
    @Override
    public ArticleReviewResponseDto updateArticleReview(ArticleReviewRequestDto requestDto, long id, Member member) {
        Article currentArticle = articleRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        String modifiedReview = currentArticle.updateArticleReview(requestDto);
        return ArticleReviewResponseDto.builder()
                .review(modifiedReview)
                .build();
    }

    // 리뷰Hide 수정
    @Override
    public ArticleReviewHideResponseDto updateArticleReviewHide(long id) {
        Article currentArticle = articleRepository.findById(id).orElseThrow(IllegalAccessError::new);
        boolean reviewHide = currentArticle.isReviewHide();
        return ArticleReviewHideResponseDto.builder()
                .reviewHide(reviewHide)
                .build();
    }
}