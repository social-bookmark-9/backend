package com.sparta.backend.serviceImpl;

import com.sparta.backend.model.Article;
import com.sparta.backend.model.ArticleFolder;
import com.sparta.backend.model.Hashtag;
import com.sparta.backend.model.Member;
import com.sparta.backend.repository.ArticleFolderRepository;
import com.sparta.backend.repository.ArticleRepository;
import com.sparta.backend.repository.HashtagRepository;
import com.sparta.backend.repository.MemberRepository;
import com.sparta.backend.requestDto.ArticleFolderCreateRequestDto;
import com.sparta.backend.responseDto.ArticleFolderListResponseDto;
import com.sparta.backend.responseDto.MemberInfoResponseDto;
import com.sparta.backend.service.ArticleFolderService;
import com.sparta.backend.service.MyPageService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@SpringBootTest
@Transactional
@Rollback(value = true)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MyPageServiceImplTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    ArticleFolderRepository articleFolderRepository;
    @Autowired
    HashtagRepository hashtagRepository;
    @Autowired
    ArticleFolderService articleFolderService;
    @Autowired
    MyPageService myPageService;

    @PersistenceContext
    private EntityManager em;

}