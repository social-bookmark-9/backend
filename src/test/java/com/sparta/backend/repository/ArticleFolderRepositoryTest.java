package com.sparta.backend.repository;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.backend.model.*;

import com.sparta.backend.responseDto.MainPageArticleFolderResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.sparta.backend.model.QArticleFolder.*;
import static com.sparta.backend.model.QHashtag.*;

@SpringBootTest
@Transactional
class ArticleFolderRepositoryTest {

    @Autowired
    ArticleFolderRepository articleFolderRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void myPageArticleFolder() {
        Optional<Member> member1L = memberRepository.findById(1L);
        System.out.println("--------------------------------------------------------------------------------------------");
        List<ArticleFolder> articleFolderByMemberId = articleFolderRepository.myPageArticleFolder(member1L.get());
        for (ArticleFolder articleFolder : articleFolderByMemberId) {
            System.out.println("articleFolderName" + articleFolder.getArticleFolderName());
            articleFolder.getArticles().stream().map(Article::getTitleOg).forEach(s -> System.out.println("titleOg = " + s));
            articleFolder.getArticles().stream().map(Article::getContentOg).forEach(s -> System.out.println("contentOg = " + s));
        }
    }

    @Test
    void mainPageArticleFolderLogin() {
        List<String> hashTagList = Arrays.asList("IT");
        List<MainPageArticleFolderResponseDto> mainPageAfDtoList = articleFolderRepository.mainPageArticleFolderLogin(1L, hashTagList);
        for (MainPageArticleFolderResponseDto mainPageArticleFolderResponseDto : mainPageAfDtoList) {
            System.out.println(mainPageArticleFolderResponseDto.getFolderId());
        }
    }

    @Test
    void mainPageArticleFolderNonLogin() {
        List<MainPageArticleFolderResponseDto> dtoList = articleFolderRepository.mainPageArticleFolderNonLogin();
        for (MainPageArticleFolderResponseDto mainPageArticleFolderResponseDto : dtoList) {
            System.out.println(mainPageArticleFolderResponseDto.getFolderId());
        }
    }

}







