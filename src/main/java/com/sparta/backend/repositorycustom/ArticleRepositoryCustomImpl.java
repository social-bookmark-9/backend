package com.sparta.backend.repositorycustom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.backend.responseDto.ArticleRandomResponseDto;
import com.sparta.backend.responseDto.QArticleRandomResponseDto;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static com.sparta.backend.model.QArticle.article;
import static com.sparta.backend.model.QArticleFolder.*;
import static com.sparta.backend.model.QHashtag.hashtag;

public class ArticleRepositoryCustomImpl implements ArticleRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public ArticleRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<ArticleRandomResponseDto> mainPageArticleLogin(Long memberId, List<String> hashtagList, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return
                queryFactory
                        .select(new QArticleRandomResponseDto(
                                article.id.as("articleId"),
                                article.titleOg.as("titleOg"),
                                article.imgOg.as("imgOg"),
                                article.contentOg.as("contentOg"),
                                hashtag.hashtag1.as("hashtag1"),
                                hashtag.hashtag2.as("hashtag2"),
                                hashtag.hashtag3.as("hashtag3")
                        ))
                        .from(article)
                        .join(articleFolder)
                        .on(articleFolder.id.eq(article.articleFolder.id), articleFolder.folderHide.eq(false))
                        .join(hashtag)
                        .on(hashtag.article.id.eq(article.id))
                        .where(article.member.id.ne(memberId), article.createdAt.goe(startDateTime), article.createdAt.lt(endDateTime) ,hashtag.hashtag1.in(hashtagList))
                        .orderBy(article.createdAt.desc())
                        .limit(50)
                        .fetch()
        ;
    }

    @Override
    public List<ArticleRandomResponseDto> mainPageArticleNonLogin(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return
                queryFactory
                        .select(new QArticleRandomResponseDto(
                                article.id.as("articleId"),
                                article.titleOg.as("titleOg"),
                                article.imgOg.as("imgOg"),
                                article.contentOg.as("contentOg"),
                                hashtag.hashtag1.as("hashtag1"),
                                hashtag.hashtag2.as("hashtag2"),
                                hashtag.hashtag3.as("hashtag3")
                        ))
                        .from(article)
                        .join(articleFolder)
                        .on(articleFolder.id.eq(article.articleFolder.id), articleFolder.folderHide.eq(false))
                        .join(hashtag)
                        .on(hashtag.article.id.eq(article.id))
                        .where(article.createdAt.goe(startDateTime), article.createdAt.lt(endDateTime))
                        .orderBy(article.createdAt.desc())
                        .limit(50)
                        .fetch()
                ;
    }

}
