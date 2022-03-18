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
import com.sparta.backend.requestDto.ReminderRequestDto;
import com.sparta.backend.responseDto.*;
import com.sparta.backend.service.ArticleService;
import com.sparta.backend.service.ReminderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleServiceImpl implements ArticleService {
    private final ReminderService reminderService;
    private final ArticleRepository articleRepository;
    private final ArticleFolderRepository articleFolderRepository;

    // 랜덤 아티클 뽑기
    private HashSet<Article> getRandomArticles(List<Article> articles) {
        HashSet<Article> relatedArticles = new HashSet<>();
        Random random = new Random();
        int bound = articles.size();
        while (relatedArticles.size() < 9) {
            int randomNum = random.nextInt(bound);
            relatedArticles.add(articles.get(randomNum));
        }
        return relatedArticles;
    }

    // 특정 아티클 조회
    @Override
    public ArticleGetResponseDto getArticle(Long id, Member member) {
        Article article = articleRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 아티클이 없습니다."));

        // 1. 현재 아티클의 1번 해시태그 가져오기
        String mainHashtag = article.getHashtag().getHashtag1();

        // 2. 1번 해시태그와 맞는 모든 아티클 불러오기
        List<Article> articles = articleRepository.findArticlesByHashtag_Hashtag1(mainHashtag);

        // 3. 9개를 랜덤으로 뽑아서 가져오기
        List<Article> randomArticles;
        if (articles.size() > 8) { randomArticles = new ArrayList<>(getRandomArticles(articles)); }
        else { randomArticles = articles; }

        List<ArticleRandomResponseDto> randomResponseDtos = new ArrayList<>();
        for (Article randomArticle : randomArticles) {
            ArticleRandomResponseDto responseDto = ArticleRandomResponseDto.builder()
                    .articleId(randomArticle.getId())
                    .titleOg(randomArticle.getTitleOg())
                    .imgOg(randomArticle.getImgOg())
                    .contentOg(randomArticle.getContentOg())
                    .hashtag1(randomArticle.getHashtag().getHashtag1())
                    .hashtag2(randomArticle.getHashtag().getHashtag2())
                    .hashtag3(randomArticle.getHashtag().getHashtag3())
                    .build();
            randomResponseDtos.add(responseDto);
        }

        return ArticleGetResponseDto.builder()
                .articleId(article.getId())
                .url(article.getUrl())
                .titleOg(article.getTitleOg())
                .imgOg(article.getImgOg())
                .contentOg(article.getContentOg())
                .hashtag1(article.getHashtag().getHashtag1())
                .hashtag2(article.getHashtag().getHashtag2())
                .hashtag3(article.getHashtag().getHashtag3())
                .review(article.getReview())
                .reviewHide(article.getReviewHide())
                .reminderDate(article.getReminder().getButtonDate())
                .articleFolderName(article.getArticleFolder().getArticleFolderName())
                .recommendArticles(randomResponseDtos)
                .build();
    }

    // 아티클 생성
    @Override
    public ArticleCreateResponseDto createArticle(ArticleCreateRequestDto requestDto, Member member) {

        ArticleFolder articleFolder = articleFolderRepository.findArticleFolderByArticleFolderName(requestDto.getArticleFolderName());

        Hashtag hashtag = Hashtag.builder().
                hashtag1(requestDto.getHashtag1())
                .hashtag2(requestDto.getHashtag2())
                .hashtag3(requestDto.getHashtag3())
                .build();

        Article article = Article.builder()
                .url(requestDto.getUrl())
                .titleOg(requestDto.getTitleOg())
                .imgOg(requestDto.getImgOg())
                .contentOg(requestDto.getContentOg())
                .readCount(requestDto.getReadCount())
                .hashtag(hashtag)
                .articleFolder(articleFolder)
                .build();

        article.setArticleFolder(articleFolder);
        article.setHashtag(hashtag);
        hashtag.setArticle(article);
        articleRepository.save(article);

        if (requestDto.getReminderDate() != 0) {
            ReminderRequestDto requestDto1 = ReminderRequestDto.builder()
                    .titleOg(requestDto.getTitleOg())
                    .buttonDate(requestDto.getReminderDate())
                    .url(requestDto.getUrl())
                    .articleId(article.getId())
                    .build();
            reminderService.createReminder(requestDto1, member);
        }

        return ArticleCreateResponseDto.builder()
                .articleId(article.getId())
                .createdAt(article.getCreatedAt())
                .url(article.getUrl())
                .titleOg(article.getTitleOg())
                .imgOg(article.getImgOg())
                .contentOg(article.getContentOg())
                .hashtag1(article.getHashtag().getHashtag1())
                .hashtag2(article.getHashtag().getHashtag2())
                .hashtag3(article.getHashtag().getHashtag3())
                .isMe(true)
                .isRead(true)
                .isSaved(true)
                .build();
    }

    // 아티클의 아티클 폴더 변경
    @Override
    public void updateArticle(ArticleUpdateRequestDto requestDto, Long id, Member member) {
        Article currentArticle = articleRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        // 1. currentArticle 의 currentArticleFolder 를 찾기
        ArticleFolder currentArticleFolder = currentArticle.getArticleFolder();
        String enteredArticleFolderName = requestDto.getArticleFolder().getArticleFolderName();
        // 2. currentArticleFolder 에서 currentArticle 을 제거
        currentArticleFolder.deleteArticleFromArticleFolder(currentArticle);
        // 3. 입력받은 ArticleFolderName 으로 ArticleFolder 객체를 찾기
        ArticleFolder toMoveArticleFolder = articleFolderRepository.findArticleFolderByArticleFolderName(enteredArticleFolderName);
        // 4. 찾은 ArticleFolder 에 currentArticle 을 Add 하기
        toMoveArticleFolder.getArticles().add(currentArticle);
        currentArticle.updateArticle(requestDto);
    }

    // 리뷰 수정
    @Override
    public ArticleReviewResponseDto updateArticleReview(ArticleReviewRequestDto requestDto, Long id, Member member) {
        Article currentArticle = articleRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아티클이 존재하지 않습니다."));
        String modifiedReview = currentArticle.updateArticleReview(requestDto);
        return ArticleReviewResponseDto.builder().review(modifiedReview).build();
    }

    // 리뷰Hide 수정
    @Override
    public ArticleReviewHideResponseDto updateArticleReviewHide(Long id) {
        Article currentArticle = articleRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아티클이 존재하지 않습니다."));
        boolean reviewHide = currentArticle.getReviewHide();
        return ArticleReviewHideResponseDto.builder().reviewHide(reviewHide).build();
    }
}