//package com.sparta.backend.repository;
//
//import com.sparta.backend.model.ArticleFolder;
//import com.sparta.backend.model.Favorite;
//import com.sparta.backend.model.Hashtag;
//import com.sparta.backend.model.Member;
//import com.sparta.backend.requestDto.ArticleFolderCreateRequestDto;
//import com.sparta.backend.service.ArticleFolderService;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Commit;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.*;
//
//@SpringBootTest
//@Transactional
//class FavoriteRepositoryTest {
//
//    @Autowired
//    FavoriteRepository favoriteRepository;
//    @Autowired
//    MemberRepository memberRepository;
//    @Autowired
//    ArticleFolderRepository articleFolderRepository;
//    @Autowired
//    ArticleFolderService articleFolderService;
//
//    @PersistenceContext
//    private EntityManager em;
//
//    @Test
//    void findAllFolderId() {
//        // 회원 등록
//        Hashtag memberHashtag = Hashtag.builder()
//                .hashtag1("IT")
//                .hashtag2("주식")
//                .hashtag3("스포츠")
//                .build();
//
//        Member testMember = Member.builder()
//                .kakaoId("test")
//                .memberName("test")
//                .email("test@test.com")
//                .password("test")
//                .profileImage("https://image.com")
//                .hashtag(memberHashtag)
//                .memberRoles(Arrays.asList("USER", "ADMIN"))
//                .build();
//
//        memberHashtag.setMember(testMember);
//        memberRepository.save(testMember);
//
//        em.flush();
//        em.clear();
//
//        // 폴더 생성
//        Optional<Member> findMember = memberRepository.findAll().stream().findFirst();
//
//        ArticleFolderCreateRequestDto articleFolderCreateRequestDto1 =
//                new ArticleFolderCreateRequestDto("testFolder1", false);
//
//        articleFolderService.createArticleFolder(articleFolderCreateRequestDto1, findMember.get());
//
//        ArticleFolderCreateRequestDto articleFolderCreateRequestDto2 =
//                new ArticleFolderCreateRequestDto("testFolder2", false);
//
//        articleFolderService.createArticleFolder(articleFolderCreateRequestDto2, findMember.get());
//
//        ArticleFolderCreateRequestDto articleFolderCreateRequestDto3 =
//                new ArticleFolderCreateRequestDto("testFolder3", false);
//
//        articleFolderService.createArticleFolder(articleFolderCreateRequestDto3, findMember.get());
//
//        em.flush();
//        em.clear();
//
//        //좋아요 생성
//        Optional<Member> findMember2 = memberRepository.findAll().stream().findFirst();
//        List<ArticleFolder> allFolder = articleFolderRepository.findAll();
//
//        Favorite favorite1 = new Favorite(allFolder.get(0), findMember2.get());
//        Favorite favorite2 = new Favorite(allFolder.get(1), findMember2.get());
//        Favorite favorite3 = new Favorite(allFolder.get(2), findMember2.get());
//        favoriteRepository.save(favorite1);
//        favoriteRepository.save(favorite2);
//        favoriteRepository.save(favorite3);
//
//        em.flush();
//        em.clear();
//
//        // memberId로 종아요의 folderId 가져오기
//        Optional<Member> findMember3 = memberRepository.findAll().stream().findFirst();
//        List<Long> allFolderId = favoriteRepository.findAllFolderId(findMember3.get().getId());
//
//        assertThat(allFolderId.get(0)).isEqualTo(1L);
//        assertThat(allFolderId.get(1)).isEqualTo(2L);
//        assertThat(allFolderId.get(2)).isEqualTo(3L);
//    }
//
//}