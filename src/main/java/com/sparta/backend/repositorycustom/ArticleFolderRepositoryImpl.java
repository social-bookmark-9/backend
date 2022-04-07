package com.sparta.backend.repositorycustom;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.backend.model.ArticleFolder;
import com.sparta.backend.model.Member;
import com.sparta.backend.responseDto.MainPageArticleFolderResponseDto;
import com.sparta.backend.responseDto.QMainPageArticleFolderResponseDto;
import com.sparta.backend.responseDto.QMainPageArticleFolderResponseDto_ArticleTitleContentDto;

import javax.persistence.EntityManager;

import java.util.List;

import static com.querydsl.core.group.GroupBy.*;
import static com.sparta.backend.model.QArticle.*;
import static com.sparta.backend.model.QArticleFolder.articleFolder;
import static com.sparta.backend.model.QHashtag.hashtag;
import static com.sparta.backend.model.QMember.*;

public class ArticleFolderRepositoryImpl implements ArticleFolderRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ArticleFolderRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<ArticleFolder> myPageArticleFolder(Member member) {
        return
                queryFactory
                        .selectFrom(articleFolder)
                        .leftJoin(articleFolder.articles, article).fetchJoin()
                        .where(articleFolder.member.eq(member)).distinct()
                        .fetch();
    }

    @Override
    public List<MainPageArticleFolderResponseDto> mainPageArticleFolderLogin(Long memberId, List<String> hashTagList) {
        return
                queryFactory
                        .from(articleFolder)
                        .leftJoin(articleFolder.articles, article)
                        .where(memberIdNe(memberId),
                                mustNotHide(articleFolder.id),
                                mustNotDeleteable(articleFolder.id),
                                folderHashTag1In(hashTagList))
                        .distinct()
                        .orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc(), articleFolder.likeCount.desc())
                        .limit(9)
                        .transform(
                                groupBy(articleFolder.id)
                                        .list(new QMainPageArticleFolderResponseDto(
                                                articleFolder.member.id.as("memerId"),
                                                articleFolder.id.as("folderId"),
                                                articleFolder.articleFolderName.as("folderName"),
                                                articleFolder.likeCount,
                                                articleFolder.folderHashtag1.as("hashTag1"),
                                                articleFolder.folderHashtag2.as("hashTag2"),
                                                articleFolder.folderHashtag3.as("hashTag3"),
                                                list(
                                                    new QMainPageArticleFolderResponseDto_ArticleTitleContentDto(
                                                            article.titleOg,
                                                            article.contentOg
                                                    )
                                                )
                                        ))
                        );
    }

    @Override
    public List<MainPageArticleFolderResponseDto> mainPageArticleFolderNonLogin() {
        return
                queryFactory
                        .from(articleFolder)
                        .leftJoin(articleFolder.articles, article)
                        .where(mustNotHide(articleFolder.id), mustNotDeleteable(articleFolder.id))
                        .distinct()
                        .orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc(), articleFolder.likeCount.desc())
                        .limit(9)
                        .transform(
                                groupBy(articleFolder.id)
                                        .list(new QMainPageArticleFolderResponseDto(
                                                articleFolder.member.id.as("memerId"),
                                                articleFolder.id.as("folderId"),
                                                articleFolder.articleFolderName.as("folderName"),
                                                articleFolder.likeCount,
                                                articleFolder.folderHashtag1.as("hashTag1"),
                                                articleFolder.folderHashtag2.as("hashTag2"),
                                                articleFolder.folderHashtag3.as("hashTag3"),
                                                list(
                                                        new QMainPageArticleFolderResponseDto_ArticleTitleContentDto(
                                                                article.titleOg,
                                                                article.contentOg
                                                        )
                                                )
                                        ))
                        );
    }

    @Override
    public List<String> articleFolderHashtagInfo(Long memberId) {
        return null;
    }

    private BooleanExpression memberIdNe(Long memberId) {
        return memberId != null ? articleFolder.member.id.ne(memberId) : null;
    }

    private BooleanExpression mustNotHide(NumberPath<Long> id) {
        return id != null ? articleFolder.folderHide.eq(false) : null;
    }

    private BooleanExpression mustNotDeleteable(NumberPath<Long> id) {
        return id != null ? articleFolder.deleteable.eq(true) : null;
    }

    private BooleanExpression folderHashTag1In(List<String> hashTagList) {
        return articleFolder.folderHashtag2 != null ? articleFolder.folderHashtag2.in(hashTagList) : null;
    }

    private BooleanExpression folderHashTag2In(List<String> hashTagList) {
        return articleFolder.folderHashtag1 != null ? articleFolder.folderHashtag1.in(hashTagList) : null;
    }

    private BooleanExpression folderHashTag3In(List<String> hashTagList) {
        return articleFolder.folderHashtag3 != null ? articleFolder.folderHashtag3.in(hashTagList) : null;
    }

}
