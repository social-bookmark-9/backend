package com.sparta.backend.serviceImpl;

import com.sparta.backend.model.Article;
import com.sparta.backend.model.ArticleFolder;
import com.sparta.backend.model.Member;
import com.sparta.backend.repository.ArticleFolderRepository;
import com.sparta.backend.repository.ArticleRepository;
import com.sparta.backend.repository.MemberRepository;
import com.sparta.backend.requestDto.ArticleFolderCreateRequestDto;
import com.sparta.backend.requestDto.ArticleFolderNameUpdateRequestDto;
import com.sparta.backend.responseDto.ArticlesInFolderResponseDto;
import com.sparta.backend.service.ArticleFolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class ArticleFolderServiceImpl implements ArticleFolderService {

    private final MemberRepository memberRepository;
    private final ArticleFolderRepository articleFolderRepository;
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleFolderServiceImpl(MemberRepository memberRepository ,ArticleFolderRepository articleFolderRepository,
                                    ArticleRepository articleRepository) {
        this.memberRepository = memberRepository;
        this.articleFolderRepository = articleFolderRepository;
        this.articleRepository = articleRepository;
    }

    /**
     * 폴더 생성
     * @param articleFolderRequestDto
     * @param member
     */
    @Override
    public void createArticleFolder(ArticleFolderCreateRequestDto articleFolderRequestDto, Member member) {
        ArticleFolder articleFolder = ArticleFolder.builder()
                .articleFolderName(articleFolderRequestDto.getArticleFolderName())
                .deleteable(true)
                .folderHide(articleFolderRequestDto.getFolderHide())
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
    public void deleteArticleFolder(Long id) {
        getFolder(id).ifPresent(
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
    public void updateArticleFolderName(ArticleFolderNameUpdateRequestDto articleFolderNameUpdateRequestDto, Long id) {
        String articleFolderName = articleFolderNameUpdateRequestDto.getArticleFolderName();
        getFolder(id).ifPresent(
                articleFolder -> articleFolderRepository.updateArticleFolderTitle(articleFolderName, id)
        );
    }

    /**
     * 폴더 안 아티클 조회
     * @param id
     * @return List<ArticlesInFolderResponseDto>
     */
    @Override
    public List<ArticlesInFolderResponseDto> findArticlesInFolder(Member member, Long id) {
        List<ArticlesInFolderResponseDto> articlesInFolderResponseDtoList = new ArrayList<>();

        // find 아티클 폴더
        Optional<ArticleFolder> articleFolder = getFolder(id);
        List<Article> articles = new ArrayList<>();

        // 폴더 안 모든 아티클 articles에 저장
        articleFolder.map(ArticleFolder::getArticles).ifPresent(
                articleList -> articleList.forEach(article -> articles.add(article))
        );

        List<Article> myArticles     = new ArrayList<>();
        member.getArticleFolders().stream().map(ArticleFolder::getArticles)
                .forEach();



        // isMe
        boolean isMe = false;
        if (articleFolder.isPresent() && articleFolder.get().getMember().equals(member)) {
            isMe = true;
        }

        for (Article article : articles) {
            boolean isRead = article.getReadCount() > 0;

            ArticlesInFolderResponseDto.builder()
                    .articleId(article.getId())
                    .url(article.getUrl())
                    .titleOg(article.getTitleOg())
                    .imgOg(article.getTitleOg())
                    .contentOg(article.getContentOg())
                    .hashtag(article.getHashtag())
                    .isMe(isMe)
                    .isRead(isRead)
                    .isSaved()
                    .build();
        }




        return articlesInFolderResponseDtoList;
    }

    /**
     * 폴더 안 아티클 삭제
     * @param folderId
     * @param articleId
     */
    @Override
    public void deleteArticleInArticleFolder(Long folderId, Long articleId) {
        Optional<ArticleFolder> articleFolder = getFolder(folderId);
        Optional<List<Article>> articles = articleFolder.map(ArticleFolder::getArticles);
        if (articles.isPresent()) {
            List<Article> targetArticle = articles
                    .get()
                    .stream()
                    .filter(article -> article.getId().equals(articleId))
                    .collect(Collectors.toList());
            articleFolder.get().getArticles().remove(targetArticle.get(0));
        } else {
            throw new IllegalArgumentException();
        }
    }


    /**
     * 폴더 ID에 해당되는 폴더 조회 메소드
     * @param id
     * @return Optional<ArticleFolder>
     */
    private Optional<ArticleFolder> getFolder(Long id) {
        return articleFolderRepository.findById(id);
    }


}

