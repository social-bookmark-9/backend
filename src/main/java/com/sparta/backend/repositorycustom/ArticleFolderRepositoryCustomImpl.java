package com.sparta.backend.repositorycustom;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.backend.model.ArticleFolder;
import com.sparta.backend.model.Member;
import com.sparta.backend.responseDto.MainAndSearchPageArticleFolderResponseDto;
import com.sparta.backend.responseDto.QMainAndSearchPageArticleFolderResponseDto;
import com.sparta.backend.responseDto.QMainAndSearchPageArticleFolderResponseDto_ArticleTitleContentDto;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

import static com.querydsl.core.group.GroupBy.*;
import static com.sparta.backend.model.QArticle.*;
import static com.sparta.backend.model.QArticleFolder.articleFolder;

public class ArticleFolderRepositoryCustomImpl implements ArticleFolderRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ArticleFolderRepositoryCustomImpl(EntityManager em) {
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
    public List<MainAndSearchPageArticleFolderResponseDto> mainPageArticleFolderLogin(Long memberId, List<String> hashTagList) {
        Double tlkAvg = queryFactory
                .select(articleFolder.likeCount.avg())
                .from(articleFolder)
                .where(articleFolder.likeCount.gt(0))
                .fetchOne();

        List<Long> ids = queryFactory
                .select(articleFolder.id)
                .from(articleFolder)
                .where(gtParticularLikeCount(tlkAvg) ,articleFolder.folderHide.eq(false), articleFolder.deleteable.eq(true), articleFolder.member.id.ne(memberId), folderHashtagsIn(hashTagList))
                .orderBy(articleFolder.likeCount.desc())
                .limit(50)
                .fetch();

        if (ids.isEmpty()) {
            return new ArrayList<>();
        }

        return
                queryFactory
                        .from(articleFolder)
                        .leftJoin(article)
                        .on(article.articleFolder.id.eq(articleFolder.id))
                        .where(articleFolder.id.in(ids))
                        .transform(
                                groupBy(articleFolder.id)
                                        .list(new QMainAndSearchPageArticleFolderResponseDto(
                                                articleFolder.member.id.as("memberId"),
                                                articleFolder.id.as("folderId"),
                                                articleFolder.articleFolderName.as("folderName"),
                                                articleFolder.likeCount,
                                                articleFolder.folderHashtag1.as("hashTag1"),
                                                articleFolder.folderHashtag2.as("hashTag2"),
                                                articleFolder.folderHashtag3.as("hashTag3"),
                                                list(
                                                        new QMainAndSearchPageArticleFolderResponseDto_ArticleTitleContentDto(
                                                                article.titleOg,
                                                                article.contentOg
                                                        )
                                                )
                                        ))
                        );
    }

    @Override
    public List<MainAndSearchPageArticleFolderResponseDto> mainPageArticleFolderNonLogin(String hashtag) {
        Double tlkAvg = queryFactory
                .select(articleFolder.likeCount.avg())
                .from(articleFolder)
                .where(articleFolder.likeCount.gt(0))
                .fetchOne();

        List<Long> ids = queryFactory
                .select(articleFolder.id)
                .from(articleFolder)
                .where(gtParticularLikeCount(tlkAvg), articleFolder.folderHashtag1.eq(hashtag), articleFolder.folderHide.eq(false), articleFolder.deleteable.eq(true))
                .orderBy(articleFolder.likeCount.desc())
                .limit(50)
                .fetch();

        if (ids.isEmpty()) {
            return new ArrayList<>();
        }

        return
                queryFactory
                        .from(articleFolder)
                        .leftJoin(article)
                        .on(article.articleFolder.id.eq(articleFolder.id))
                        .where(articleFolder.id.in(ids))
                        .transform(
                                groupBy(articleFolder.id)
                                        .list(new QMainAndSearchPageArticleFolderResponseDto(
                                                articleFolder.member.id.as("memberId"),
                                                articleFolder.id.as("folderId"),
                                                articleFolder.articleFolderName.as("folderName"),
                                                articleFolder.likeCount,
                                                articleFolder.folderHashtag1.as("hashTag1"),
                                                articleFolder.folderHashtag2.as("hashTag2"),
                                                articleFolder.folderHashtag3.as("hashTag3"),
                                                list(
                                                        new QMainAndSearchPageArticleFolderResponseDto_ArticleTitleContentDto(
                                                                article.titleOg,
                                                                article.contentOg
                                                        )
                                                )
                                        ))
                        );
    }

    private BooleanExpression gtParticularLikeCount(Double likeCount) {
        return articleFolder.likeCount.gt(likeCount);
    }

    private BooleanExpression mustNotHide() {
        return articleFolder.folderHide.eq(false);
    }

    private BooleanExpression mustDeleteable() {
        return articleFolder.deleteable.eq(true);
    }

    private BooleanExpression folderHashtagsIn(List<String> hashTagList) {
        return articleFolder.folderHashtag1 != null ? articleFolder.folderHashtag1.in(hashTagList) : null;
    }
}
