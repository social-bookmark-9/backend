package com.sparta.backend.serviceImpl;

import com.sparta.backend.model.Article;
import com.sparta.backend.model.ArticleFolder;
import com.sparta.backend.model.Member;
import com.sparta.backend.repository.ArticleFolderRepository;
import com.sparta.backend.repository.MemberRepository;
import com.sparta.backend.requestDto.CreateArticleFolderReqDto;
import com.sparta.backend.requestDto.UpdateAFNameReqDto;
import com.sparta.backend.responseDto.ArticlesInFolderRespDto;
import com.sparta.backend.service.ArticleFolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public void createArticleFolder(CreateArticleFolderReqDto articleFolderRequestDto, Member member) {
        checkMember(member.getId());

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
     * @param updateAFNameReqDto
     * @param id
     * @return void
     */
    @Override
    public void updateArticleFolderName(UpdateAFNameReqDto updateAFNameReqDto, Long id) {
        String articleFolderName = updateAFNameReqDto.getArticleFolderName();
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
    public List<ArticlesInFolderRespDto> findArticlesInFolder(Long id) {
        List<ArticlesInFolderRespDto> articlesInFolderRespDtoList = new ArrayList<>();

        List<Article> articles = checkFolder(id).get().getArticles();
        for (Article article : articles) {
            ArticlesInFolderRespDto articlesInFolderRespDto = ArticlesInFolderRespDto.builder()
                    .url(article.getUrl())
                    .titleOg(article.getTitleOg())
                    .imgOg(article.getImgOg())
                    .hashtag(article.getHashtag())
                    .build();
            articlesInFolderRespDtoList.add(articlesInFolderRespDto);
        }

        return articlesInFolderRespDtoList;
    }

    /**
     * 회원 유효성 체크
     * @param id
     * @return void
     */
    private void checkMember(Long id) {
        memberRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
    }

    /**
     * 아티클 유효성 체크
     * @param id
     * @return ArticleFolder
     */
    private Optional<ArticleFolder> checkFolder(Long id) {
        return Optional.ofNullable(articleFolderRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new));
    }

}

