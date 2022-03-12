package com.sparta.backend.serviceImpl;

import com.sparta.backend.model.Article;
import com.sparta.backend.model.ArticleFolder;
import com.sparta.backend.model.Member;
import com.sparta.backend.repository.ArticleFolderRepository;
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

@Service
@Transactional
public class ArticleFolderServiceImpl implements ArticleFolderService {

    private final MemberRepository memberRepository;
    private final ArticleFolderRepository articleFolderRepository;

    @Autowired
    public ArticleFolderServiceImpl(MemberRepository memberRepository ,ArticleFolderRepository articleFolderRepository) {
        this.memberRepository = memberRepository;
        this.articleFolderRepository = articleFolderRepository;
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
        checkFolder(id).ifPresent(
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
        checkFolder(id).ifPresent(
                articleFolder -> articleFolderRepository.updateArticleFolderTitle(articleFolderName, id)
        );
    }

    /**
     * 폴더 안 아티클 조회
     * @param id
     * @return List<ArticlesInFolderRespDto>
     */
    @Override
    public List<ArticlesInFolderResponseDto> findArticlesInFolder(Long id) {
        List<ArticlesInFolderResponseDto> articlesInFolderResponseDtoList = new ArrayList<>();

        List<Article> articles = checkFolder(id).get().getArticles();
        for (Article article : articles) {
            ArticlesInFolderResponseDto articlesInFolderResponseDto = ArticlesInFolderResponseDto.builder()
                    .url(article.getUrl())
                    .titleOg(article.getTitleOg())
                    .imgOg(article.getImgOg())
                    .hashtag(article.getHashtag())
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
    public void deleteArticleInArticleFolder(Long folderId, Long articleId) {
        Optional<ArticleFolder> articleFolder = checkFolder(folderId);
        Optional<List<Article>> articles = articleFolder.map(ArticleFolder::getArticles);
        if (articles.isPresent()) {
            List<Article> targetArticle = articles.get().stream().filter(article -> article.getId().equals(articleId))
                    .collect(Collectors.toList());
            articleFolder.get().getArticles().remove(targetArticle.get(0));
        } else {
            throw new IllegalArgumentException();
        }
    }


    /**
     * 폴더 유효성 체크
     * @param id
     * @return ArticleFolder
     */
    private Optional<ArticleFolder> checkFolder(Long id) {
        return Optional.ofNullable(articleFolderRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new));
    }

}

