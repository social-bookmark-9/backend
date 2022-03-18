package com.sparta.backend.serviceImpl;

import com.sparta.backend.model.Article;
import com.sparta.backend.model.ArticleFolder;
import com.sparta.backend.model.Favorite;
import com.sparta.backend.model.Member;
import com.sparta.backend.repository.ArticleFolderRepository;
import com.sparta.backend.repository.ArticleRepository;
import com.sparta.backend.repository.FavoriteRepository;
import com.sparta.backend.repository.MemberRepository;
import com.sparta.backend.requestDto.ArticleFolderCreateRequestDto;
import com.sparta.backend.requestDto.ArticleFolderNameUpdateRequestDto;
import com.sparta.backend.responseDto.ArticlesInFolderResponseDto;
import com.sparta.backend.responseDto.LikeAddOrRemoveResponseDto;
import com.sparta.backend.service.ArticleFolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ArticleFolderServiceImpl implements ArticleFolderService {

    private final MemberRepository memberRepository;
    private final ArticleFolderRepository articleFolderRepository;
    private final ArticleRepository articleRepository;
    private final FavoriteRepository favoriteRepository;

    @Autowired
    public ArticleFolderServiceImpl(MemberRepository memberRepository ,ArticleFolderRepository articleFolderRepository,
                                    ArticleRepository articleRepository, FavoriteRepository favoriteRepository) {
        this.memberRepository = memberRepository;
        this.articleFolderRepository = articleFolderRepository;
        this.articleRepository = articleRepository;
        this.favoriteRepository = favoriteRepository;
    }

    /**
     * 아티클 폴더 생성
     * @param articleFolderRequestDto
     * @param member
     */
    @Override
    public void createArticleFolder(ArticleFolderCreateRequestDto articleFolderRequestDto, Member member) {
        ArticleFolder articleFolder = ArticleFolder.builder()
                .articleFolderName(articleFolderRequestDto.getArticleFolderName())
                .deleteable(true)
                .folderHide(articleFolderRequestDto.isFolderHide())
                .member(member)
                .build();

        articleFolderRepository.save(articleFolder);
    }

    /**
     * 아티클 폴더 삭제
     * @param id
     * @return void
     */
    @Override
    public void deleteArticleFolder(long id) {
        Optional<ArticleFolder> folder = getFolder(id);
        folder.ifPresent(
                articleFolderRepository::delete
        );
    }

    /**
     * 아티클 폴더 제목 수정
     * @param articleFolderNameUpdateRequestDto
     * @param id
     * @return void
     */
    @Override
    public void updateArticleFolderName(ArticleFolderNameUpdateRequestDto articleFolderNameUpdateRequestDto, long id) {
        String articleFolderName = articleFolderNameUpdateRequestDto.getArticleFolderName();
        Optional<ArticleFolder> folder = getFolder(id);
        folder.ifPresent(
                articleFolder -> articleFolderRepository.updateArticleFolderName(articleFolderName, id)
        );
    }

    /**
     * 폴더 안 아티클 조회
     * @param member, id
     * @return List<ArticlesInFolderResponseDto>
     * test 필요
     */
    @Override
    public List<ArticlesInFolderResponseDto> findArticlesInFolder(Member member, long id) {
        List<ArticlesInFolderResponseDto> articlesInFolderResponseDtoList = new ArrayList<>();

        // 타켓 아티클 폴더 찾기
        Optional<ArticleFolder> articleFolder = getFolder(id);

        // 폴더 안 모든 아티클 articles에 저장
        List<Article> articles = new ArrayList<>();
        articleFolder.map(ArticleFolder::getArticles).ifPresent(
                articleList -> articleList.forEach(article -> articles.add(article))
        );

        // member의 폴더 리스트에서 아티클 url로 같은 아티클을 가지고 있는지 판별하기 위해 아티클의 url만 리스트로 저장
        List<String> myArticlesUrl = new ArrayList<>();
        member.getArticleFolders()
                .stream()
                .map(ArticleFolder::getArticles)
                .forEach(myArticleList -> myArticleList.forEach(myArticle -> myArticlesUrl.add(myArticle.getUrl())));

        // isMe
        boolean isMe = articleFolder.isPresent() && articleFolder.get().getMember().equals(member);

        for (Article article : articles) {
            boolean isRead = article.getReadCount() > 0;
            boolean isSaved = myArticlesUrl.contains(article.getUrl());
            ArticlesInFolderResponseDto articlesInFolderResponseDto = ArticlesInFolderResponseDto.builder()
                    .articleId(article.getId())
                    .url(article.getUrl())
                    .titleOg(article.getTitleOg())
                    .imgOg(article.getTitleOg())
                    .contentOg(article.getContentOg())
                    .hashtag1(article.getHashtag().getHashtag1())
                    .hashtag2(article.getHashtag().getHashtag2())
                    .hashtag3(article.getHashtag().getHashtag3())
                    .isMe(isMe)
                    .isRead(isRead)
                    .isSaved(isSaved)
                    .build();

            articlesInFolderResponseDtoList.add(articlesInFolderResponseDto);
        }

        return articlesInFolderResponseDtoList;
    }

    /**
     * 폴더 안 아티클 삭제
     * @param folderId
     * @param articleId
     */
    @Override
    public void deleteArticleInArticleFolder(long folderId, long articleId) {
        Optional<ArticleFolder> articleFolder = getFolder(folderId);
        /**
         * 요기 Optional 제거하기
         */
        Optional<List<Article>> articles = articleFolder.map(ArticleFolder::getArticles);
        if (articles.isPresent()) {
            List<Article> targetArticle = articles
                    .get()
                    .stream()
                    .filter(article -> article.getId() == articleId)
                    .collect(Collectors.toList());
            articleFolder.get().getArticles().remove(targetArticle.get(0));
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * 좋아요 추가, 삭제
     * @param member
     * @param folderId
     * @return LikeAddOrRemoveResponseDto
     * 비동기 적용 고려
     */
    @Override
    public LikeAddOrRemoveResponseDto likeAddOrRemove(Member member, long folderId) {
        LikeAddOrRemoveResponseDto likeAddOrRemoveResponseDto = new LikeAddOrRemoveResponseDto();
        Optional<ArticleFolder> articleFolder = getFolder(folderId);
        Optional<Favorite> isFavoriteExist = favoriteRepository.findByMemberAndArticleFolder(member, articleFolder.get());
        if (isFavoriteExist.isPresent()) {
            favoriteRepository.delete(isFavoriteExist.get());
            likeAddOrRemoveResponseDto.setLikeStatus(false);
        } else {
            Favorite favorite = Favorite.builder()
                    .articleFolder(articleFolder.get())
                    .member(member)
                    .build();
            favoriteRepository.save(favorite);
            likeAddOrRemoveResponseDto.setLikeStatus(true);
        }

        return likeAddOrRemoveResponseDto;
    }

    /**
     * 폴더 ID에 해당되는 폴더 조회 메소드
     * @param id
     * @return Optional<ArticleFolder>
     */
    private Optional<ArticleFolder> getFolder(long id) {
        Optional<ArticleFolder> folder = articleFolderRepository.findById(id);
        if (folder.isPresent()) {
            return folder;
        } else {
            throw new IllegalArgumentException("존재하지 않는 회원");
        }
    }


}

