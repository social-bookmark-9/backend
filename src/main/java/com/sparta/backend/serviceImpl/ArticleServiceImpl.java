package com.sparta.backend.serviceImpl;

import com.sparta.backend.exception.ArticleAccessDeniedException;
import com.sparta.backend.exception.BusinessException;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleServiceImpl implements ArticleService {
    private final ReminderService reminderService;
    private final MemberRepository memberRepository;
    private final ArticleRepository articleRepository;
    private final ArticleFolderRepository articleFolderRepository;

    // 본인 확인
    public boolean isIdentityVerified(Article currentArticle, Member member) {
        Member currentMember = memberRepository.findById(member.getId()).orElseThrow(
                () -> new InvalidValueException(ErrorCode.ENTITY_NOT_FOUND.getErrorMessage()));
        Member writerMember = currentArticle.getMember();
        return currentMember == writerMember;
    }

    // 랜덤 아티클 생성
    public ArticleGetResponseDto randomArticleGenerator(Long id ,Article currentArticle, Member currentWriterMember) {
        RandomGenerator randomGenerator = new RandomGenerator();
        String mainHashtag = currentArticle.getHashtag().getHashtag1();
        Integer reminderButtonDate;
        if (currentArticle.getReminder() != null) { reminderButtonDate = currentArticle.getReminder().getButtonDate();}
        else { reminderButtonDate = null; }

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
                .articleId(currentArticle.getId())
                .writerMemberId(currentArticle.getMember().getId())
                .writerMemberName(currentWriterMember.getMemberName())
                .url(currentArticle.getUrl())
                .titleOg(currentArticle.getTitleOg())
                .imgOg(currentArticle.getImgOg())
                .contentOg(currentArticle.getContentOg())
                .hashtag1(currentArticle.getHashtag().getHashtag1())
                .hashtag2(currentArticle.getHashtag().getHashtag2())
                .hashtag3(currentArticle.getHashtag().getHashtag3())
                .review(currentArticle.getReview())
                .reviewHide(currentArticle.getReviewHide())
                .reminderDate(reminderButtonDate)
                .articleFolderName(currentArticle.getArticleFolder().getArticleFolderName())
                .recommendArticles(randomResponseDtos)
                .build();
    }

    // 아티클 생성 ✅
    @Override
    public ArticleCreateResponseDto createArticle(ArticleCreateRequestDto requestDto, Member member) {
        JsoupParser parser = new JsoupParser();
        OGTagRequestDto ogTagRequestDto = parser.ogTagScraper(requestDto.getUrl());

        Member currentMember = memberRepository.findById(member.getId()).orElseThrow(
                () -> new InvalidValueException(ErrorCode.ENTITY_NOT_FOUND.getErrorMessage()));

        ArticleFolder articleFolder = articleFolderRepository
                .findArticleFolderByArticleFolderNameAndMember(requestDto.getArticleFolderName(), currentMember);


        // contentOg 자르기 (50자 제한)
        String contentOgSub = ogTagRequestDto.getContentOg() == null ? null
                : ogTagRequestDto.getContentOg().substring(0, 50);

        Hashtag hashtag = Hashtag.builder()
                .hashtag1(requestDto.getHashtag1())
                .hashtag2(requestDto.getHashtag2())
                .hashtag3(requestDto.getHashtag3())
                .build();

        Article article = Article.builder()
                .url(requestDto.getUrl())
                .titleOg(ogTagRequestDto.getTitleOg())
                .imgOg(ogTagRequestDto.getImgOg())
                .contentOg(contentOgSub)
                .reviewHide(false)
                .readCount(requestDto.getReadCount())
                .hashtag(hashtag)
                .articleFolder(articleFolder)
                .member(currentMember)
                .build();

        article.setArticleFolder(articleFolder);
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

        // 폴더 해시태그 결정
        if (!articleFolder.getArticleFolderName().equals("미분류 컬렉션")) {
            List<String> sortedHashtag = sortingHashtag(articleFolder);

            if (sortedHashtag.size() == 1) articleFolderRepository.updateArticleFolderHashtag(sortedHashtag.get(0), null, null, articleFolder.getId());
            if (sortedHashtag.size() == 2) articleFolderRepository.updateArticleFolderHashtag(sortedHashtag.get(0), sortedHashtag.get(1), null, articleFolder.getId());
            if (sortedHashtag.size() == 3) articleFolderRepository.updateArticleFolderHashtag(sortedHashtag.get(0), sortedHashtag.get(1), sortedHashtag.get(2), articleFolder.getId());
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
                .isRead(false)
                .isSaved(true)
                .build();
    }

    // 대표 해시태그 정렬
    private List<String> sortingHashtag(ArticleFolder articleFolder) {
        Map<String, Integer> map = new HashMap<>();

        List<String> articleHashtag1List = articleFolder.getArticles()
                .stream()
                .map(eachArticle -> eachArticle.getHashtag().getHashtag1())
                .collect(Collectors.toList());

        for (String articleHasTag1 : articleHashtag1List) {
            if (map.containsKey(articleHasTag1)) {
                int cnt = map.get(articleHasTag1);
                cnt++;
                map.put(articleHasTag1, cnt);
            } else {
                map.put(articleHasTag1, 1);
            }
        }

        return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    // 특정 아티클 조회 (로그인) ✅
    @Override
    public ArticleGetResponseDto getArticleForMember(Long id, Member member) {
        Article currentArticle = articleRepository.findById(id).orElseThrow(
                () -> new InvalidValueException(ErrorCode.ENTITY_NOT_FOUND.getErrorMessage()));
        Member currentMember = memberRepository.findById(member.getId()).orElseThrow(
                () -> new InvalidValueException(ErrorCode.ENTITY_NOT_FOUND.getErrorMessage()));
        Member currentArticleMember = currentArticle.getMember();
        boolean currentArticleFolderHide = currentArticle.getArticleFolder().getFolderHide();

        if (currentArticleMember == currentMember) { return randomArticleGenerator(id, currentArticle, currentArticleMember); }
        else {
            if (currentArticleFolderHide) { throw new ArticleAccessDeniedException(ErrorCode.HANDLE_ACCESS_DENIED); }
            else { return randomArticleGenerator(id, currentArticle, currentArticleMember); }
        }
    }

    // 특정 아티클 조회 (비로그인) ✅
    @Override
    public ArticleGetResponseDto getArticleForGuest(Long id) {
        Article currentArticle = articleRepository.findById(id).orElseThrow(
                () -> new InvalidValueException(ErrorCode.ENTITY_NOT_FOUND.getErrorMessage()));
        boolean currentArticleFolderHide = currentArticle.getArticleFolder().getFolderHide();
        Member currentArticleMember = currentArticle.getMember();

        if (currentArticleFolderHide) { throw new ArticleAccessDeniedException(ErrorCode.HANDLE_ACCESS_DENIED); }
        else { return randomArticleGenerator(id, currentArticle, currentArticleMember); }
    }

    // 아티클 삭제 ✅
    @Override
    public void deleteArticle(Long id, Member member) {
        Article currentArticle = articleRepository.findById(id).orElseThrow(() ->
                new InvalidValueException(ErrorCode.ENTITY_NOT_FOUND.getErrorMessage()));
        if (isIdentityVerified(currentArticle, member)) { articleRepository.delete(currentArticle); }
        else { throw new ArticleAccessDeniedException(ErrorCode.HANDLE_ACCESS_DENIED); }
    }

    // 아티클 제목 수정 ✅
    @Override
    public ArticleTitleResponseDto updateArticleTitle(ArticleTitleRequestDto requestDto, Long id, Member member) {
        //
        Article currentArticle = articleRepository.findById(id).orElseThrow(
                () -> new InvalidValueException(ErrorCode.ENTITY_NOT_FOUND.getErrorMessage()));
        if (isIdentityVerified(currentArticle, member)) {
            String updateTitle = currentArticle.updateTitle(requestDto.getTitleOg());
            return ArticleTitleResponseDto.builder().titleOg(updateTitle).build(); }
        else { throw new ArticleAccessDeniedException(ErrorCode.HANDLE_ACCESS_DENIED); }
    }

    // 아티클 해시태그 수정 ✅
    @Override
    public void updateArticleHashtag(HashtagUpdateRequestDto requestDto, Long id, Member member) {
        Article currentArticle = articleRepository.findById(id).orElseThrow(
                () -> new InvalidValueException(ErrorCode.ENTITY_NOT_FOUND.getErrorMessage()));
        if (isIdentityVerified(currentArticle, member)) {
            Hashtag currentHashtag = currentArticle.getHashtag();
            currentHashtag.setHashtag(requestDto.getHashtag1(), requestDto.getHashtag2(), requestDto.getHashtag3());}
        else { throw new ArticleAccessDeniedException(ErrorCode.HANDLE_ACCESS_DENIED); }
    }

    // 아티클 리뷰 수정 ✅
    @Override
    public ArticleReviewResponseDto updateArticleReview(ArticleReviewRequestDto requestDto, Long id, Member member) {
        Article currentArticle = articleRepository.findById(id).orElseThrow(() ->
                new InvalidValueException(ErrorCode.ENTITY_NOT_FOUND.getErrorMessage()));
        if (isIdentityVerified(currentArticle, member)) {
            String modifiedReview = currentArticle.updateArticleReview(requestDto);
            return ArticleReviewResponseDto.builder().review(modifiedReview).build();
        }
        else { throw new ArticleAccessDeniedException(ErrorCode.HANDLE_ACCESS_DENIED);}
    }

    // 아티클 리뷰 공개여부 수정 ✅
    @Override
    public ArticleReviewHideResponseDto updateArticleReviewHide(Long id, Member member) {
        Article currentArticle = articleRepository.findById(id).orElseThrow(
                () -> new InvalidValueException(ErrorCode.ENTITY_NOT_FOUND.getErrorMessage()));
        if (isIdentityVerified(currentArticle, member)) {
            boolean updateHide = currentArticle.updateArticleReviewHide(currentArticle.getReviewHide());
            return ArticleReviewHideResponseDto.builder().reviewHide(updateHide).build();
        }
        else { throw new ArticleAccessDeniedException(ErrorCode.HANDLE_ACCESS_DENIED);}
    }

    // 아티클 모든 리뷰 조회 ✅
    @Override
    public ArticleReviewResponseDtos getArticleReviews(Member member) {
        Member currentMember = memberRepository.findById(member.getId()).orElseThrow(
                () -> new InvalidValueException(ErrorCode.ENTITY_NOT_FOUND.getErrorMessage()));
        List<Article> allArticlesByMember = articleRepository.findAllByMember(currentMember);

        List<ArticleReviewResponseDto> responseDtos = new ArrayList<>();
        for (Article article : allArticlesByMember) {
            if (article.getReview() == null) { continue; }
            ArticleReviewResponseDto responseDto = ArticleReviewResponseDto.builder()
                    .titleOg(article.getTitleOg())
                    .review(article.getReview())
                    .reviewHide(article.getReviewHide())
                    .build();
            responseDtos.add(responseDto);
        }
        return ArticleReviewResponseDtos.builder()
                .reviewList(responseDtos)
                .build();
    }

    // 아티클 읽은 횟수 증가 ✅
    @Override
    public void addArticleReadCount(Long id, Member member) {
        Article currentArticle = articleRepository.findById(id).orElseThrow(
                () -> new InvalidValueException(ErrorCode.ENTITY_NOT_FOUND.getErrorMessage()));
        if (isIdentityVerified(currentArticle, member)) { currentArticle.addReadCount(); }
        else { throw new ArticleAccessDeniedException(ErrorCode.HANDLE_ACCESS_DENIED);}
    }

    // 아티클의 폴더 이동 ✅
    @Override
    public void updateArticleFolderChange(ArticleFolderChangeUpdateRequestDto requestDto, Long id, Member member) {
        Article currentArticle = articleRepository.findById(id).orElseThrow(
                () -> new InvalidValueException(ErrorCode.ENTITY_NOT_FOUND.getErrorMessage()));

        if (isIdentityVerified(currentArticle, member)) {
            Member currentMember = memberRepository.findById(member.getId()).orElseThrow(
                    () -> new InvalidValueException(ErrorCode.ENTITY_NOT_FOUND.getErrorMessage()));
            ArticleFolder articleFolder = articleFolderRepository
                    .findArticleFolderByArticleFolderNameAndMember(requestDto.getArticleFolderName(), currentMember);

            if (articleFolder == null) { throw new InvalidValueException(ErrorCode.ENTITY_NOT_FOUND.getErrorMessage()); }
            else { currentArticle.updateArticleFolder(articleFolder); }

        } else { throw new ArticleAccessDeniedException(ErrorCode.HANDLE_ACCESS_DENIED); }
    }

    // 타유저 아티클 모두 저장 ✅
    @Override
    public void saveAllArticlesByOtherUser(ArticleFolderChangeUpdateRequestDto requestDto, Long id, Member member) {
        ArticleFolder otherUserArticleFolder = articleFolderRepository.findById(id).orElseThrow(
                () -> new InvalidValueException(ErrorCode.ENTITY_NOT_FOUND.getErrorMessage()));
        Member myMember = memberRepository.findById(member.getId()).orElseThrow(
                () -> new InvalidValueException(ErrorCode.ENTITY_NOT_FOUND.getErrorMessage()));

        ArticleFolder myArticleFolder = articleFolderRepository
                .findArticleFolderByArticleFolderNameAndMember(requestDto.getArticleFolderName(), myMember);
        Member otherUserArticleFolderMember = otherUserArticleFolder.getMember();

        if (otherUserArticleFolder.getFolderHide()) { throw new ArticleAccessDeniedException(ErrorCode.HANDLE_ACCESS_DENIED); }
        if (myMember == otherUserArticleFolderMember) { throw new BusinessException(ErrorCode.NOT_ANOTHER_USER); }
        else {
            // 가져오려는 폴더에 있는 아티클들
            List<Article> otherUserArticlesInArticleFolder = otherUserArticleFolder.getArticles();
            // 저장할 내 폴더에 있는 아티클들
            List<Article> myArticlesInArticleFolder = myArticleFolder.getArticles();

            List<Article> toSaveArticles = new ArrayList<>();
            // 저장할 내 폴더에 가져오려는 폴더의 아티클들 추가
            // 다른 사용자의 아티클들을 순회하며 각각의 값을 가져와 저장할 아티클을 생성
            for (Article otherUserArticle : otherUserArticlesInArticleFolder) {
                Hashtag hashtag = Hashtag.builder().
                        hashtag1(otherUserArticle.getHashtag().getHashtag1())
                        .hashtag2(otherUserArticle.getHashtag().getHashtag2())
                        .hashtag3(otherUserArticle.getHashtag().getHashtag3())
                        .build();

                Article toSaveArticle = Article.builder()
                        .url(otherUserArticle.getUrl())
                        .titleOg(otherUserArticle.getTitleOg())
                        .imgOg(otherUserArticle.getImgOg())
                        .contentOg(otherUserArticle.getContentOg())
                        .reviewHide(false)
                        .readCount(0)
                        .hashtag(hashtag)
                        .articleFolder(myArticleFolder)
                        .member(myMember)
                        .build();
                hashtag.setArticle(toSaveArticle);
                toSaveArticles.add(toSaveArticle);
                myArticlesInArticleFolder.add(toSaveArticle);
            }
            articleRepository.saveAll(toSaveArticles);
        }
    }
}