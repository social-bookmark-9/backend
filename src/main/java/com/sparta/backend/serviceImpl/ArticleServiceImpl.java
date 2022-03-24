package com.sparta.backend.serviceImpl;

import com.sparta.backend.exception.ArticleAccessDeniedException;
import com.sparta.backend.exception.ErrorCode;
import com.sparta.backend.exception.InvalidValueException;
import com.sparta.backend.model.Article;
import com.sparta.backend.model.ArticleFolder;
import com.sparta.backend.model.Hashtag;
import com.sparta.backend.model.Member;
import com.sparta.backend.repository.ArticleFolderRepository;
import com.sparta.backend.repository.ArticleRepository;
import com.sparta.backend.repository.MemberRepository;
import com.sparta.backend.requestDto.*;
import com.sparta.backend.responseDto.*;
import com.sparta.backend.service.ArticleService;
import com.sparta.backend.service.ReminderService;
import com.sparta.backend.utils.JsoupParser;
import com.sparta.backend.utils.RandomGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleServiceImpl implements ArticleService {
    private final ReminderService reminderService;
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;
    private final ArticleFolderRepository articleFolderRepository;

    // 랜덤 아티클 생성
    public ArticleGetResponseDto randomArticleGenerator(Long id, Article article) {
        RandomGenerator randomGenerator = new RandomGenerator();
        String mainHashtag = article.getHashtag().getHashtag1();
        List<Article> togetherArticles = articleRepository.findArticlesByIdNotAndHashtag_Hashtag1AndArticleFolder_FolderHide(id ,mainHashtag, false);
        List<Article> randomArticles;
        if (togetherArticles.size() > 8) { randomArticles = randomGenerator.getRandomArticles(togetherArticles, 9); }
        else { randomArticles = togetherArticles; }

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

    // 특정 아티클 조회 (로그인)
    @Override
    public ArticleGetResponseDto getArticleForMember(Long id, Member member) {
        Article currentArticle = articleRepository.findById(id).orElseThrow(
                () -> new InvalidValueException("존재하지 않습니다."));
        Member currentMember = memberRepository.findById(member.getId()).orElseThrow(
                () -> new InvalidValueException("존재하지 않습니다."));
        Member currentArticleMember = currentArticle.getMember();

        boolean currentArticleFolderHide = currentArticle.getArticleFolder().isFolderHide();

        if (currentArticleMember == currentMember) { return randomArticleGenerator(id, currentArticle); }
        else {
            if (currentArticleFolderHide) { throw new ArticleAccessDeniedException(ErrorCode.HANDLE_ACCESS_DENIED); }
            else { return randomArticleGenerator(id, currentArticle); }
        }
    }

    // 특정 아티클 조회 (비로그인)
    @Override
    public ArticleGetResponseDto getArticleForGuest(Long id) {
        Article currentArticle = articleRepository.findById(id).orElseThrow(
                () -> new InvalidValueException("존재하지 않습니다."));
        boolean currentArticleFolderHide = currentArticle.getArticleFolder().isFolderHide();

        if (currentArticleFolderHide) { throw new ArticleAccessDeniedException(ErrorCode.HANDLE_ACCESS_DENIED); }
        else { return randomArticleGenerator(id, currentArticle); }
    }


    // 아티클 생성
    @Override
    public ArticleCreateResponseDto createArticle(ArticleCreateRequestDto requestDto, Member member) {
        // String ogTagSeleniumTest = parser.seleniumParser(requestDto.getUrl()); // 셀레니움 테스트
        JsoupParser parser = new JsoupParser();
        OGTagRequestDto ogTagRequestDto = parser.ogTagScraper(requestDto.getUrl());

        Member currentMember = memberRepository.findById(member.getId()).orElseThrow(
                () -> new InvalidValueException("사용자가 존재하지 않습니다."));
        ArticleFolder articleFolder = articleFolderRepository.findArticleFolderByArticleFolderNameAndMember(requestDto.getArticleFolderName(), currentMember);

        Hashtag hashtag = Hashtag.builder().
                hashtag1(requestDto.getHashtag1())
                .hashtag2(requestDto.getHashtag2())
                .hashtag3(requestDto.getHashtag3())
                .build();

        Article article = Article.builder()
                .url(requestDto.getUrl())
                .titleOg(ogTagRequestDto.getTitleOg())
                .imgOg(ogTagRequestDto.getImgOg())
                .contentOg(ogTagRequestDto.getContentOg())
                .reviewHide(false)
                .readCount(requestDto.getReadCount())
                .hashtag(hashtag)
                .articleFolder(articleFolder)
                .member(currentMember)
                .build();

        article.setArticleFolder(articleFolder);
        article.setHashtag(hashtag);
        hashtag.setArticle(article);
        articleRepository.save(article);

        if (requestDto.getReminderDate() != 0) {
            ReminderRequestDto requestDto1 = ReminderRequestDto.builder()
                    .titleOg(ogTagRequestDto.getTitleOg())
                    .buttonDate(requestDto.getReminderDate())
                    .imgOg(ogTagRequestDto.getImgOg())
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
                .isRead(false)
                .isSaved(true)
                .build();
    }

    // 아티클 제거
    @Override
    public void deleteArticle(Long id, Member member) {
        Article currentArticle = articleRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아티클이 존재하지 않습니다."));
        Member currentMember = memberRepository.findById(member.getId()).orElseThrow(
                () -> new IllegalArgumentException("아티클이 존재하지 않습니다."));
        Member writerMember = currentArticle.getMember();

        if (currentMember == writerMember) { articleRepository.delete(currentArticle); }
        else { throw new ArticleAccessDeniedException(ErrorCode.HANDLE_ACCESS_DENIED); }
    }

    // 아티클의 폴더 이동
    @Override
    public void moveMyArticleToAnotherFolder(ArticleUpdateRequestDto requestDto, Long id, Member member) {
        Article currentArticle = articleRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("찾을 수 없습니다."));
        Member currentMember = memberRepository.findById(member.getId()).orElseThrow(
                () -> new IllegalArgumentException("찾을 수 없습니다."));

        ArticleFolder currentArticleFolder = currentArticle.getArticleFolder();
        String enteredArticleFolderName = requestDto.getArticleFolder().getArticleFolderName();
        currentArticleFolder.deleteArticleFromArticleFolder(currentArticle);
        ArticleFolder toMoveArticleFolder = articleFolderRepository.findArticleFolderByArticleFolderNameAndMember(enteredArticleFolderName, currentMember);
        toMoveArticleFolder.getArticles().add(currentArticle);
        currentArticle.updateArticle(requestDto);
    }

    // 리뷰(메모) 수정
    @Override
    public ArticleReviewResponseDto updateArticleReview(ArticleReviewRequestDto requestDto, Long id, Member member) {
        Article currentArticle = articleRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아티클이 존재하지 않습니다."));
        String modifiedReview = currentArticle.updateArticleReview(requestDto);
        return ArticleReviewResponseDto.builder().review(modifiedReview).build();
    }

    // 리뷰 공개여부 수정
    @Override
    public ArticleReviewHideResponseDto updateArticleReviewHide(Long id) {
        Article currentArticle = articleRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아티클이 존재하지 않습니다."));
        boolean updateHide = currentArticle.updateArticleReviewHide(currentArticle.getReviewHide());
        return ArticleReviewHideResponseDto.builder().reviewHide(updateHide).build();
    }

    // 모든 리뷰 가져오기
    @Override
    public ArticleReviewResponseDtos getReviews(Member member) {
        Member currentMember = memberRepository.findById(member.getId()).orElseThrow(
                () -> new InvalidValueException("사용자가 존재하지 않습니다."));
        List<Article> allArticlesByMember = articleRepository.findAllByMember(currentMember);

        List<ArticleReviewResponseDto> responseDtos = new ArrayList<>();
        for (Article article : allArticlesByMember) {
            ArticleReviewResponseDto responseDto = ArticleReviewResponseDto.builder()
                    .review(article.getReview())
                    .reviewHide(article.getReviewHide())
                    .build();
            responseDtos.add(responseDto);
        }
        return ArticleReviewResponseDtos.builder()
                .reviewList(responseDtos)
                .build();
    }
}