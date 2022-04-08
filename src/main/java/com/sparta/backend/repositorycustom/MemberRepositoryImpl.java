package com.sparta.backend.repositorycustom;

import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.backend.model.Hashtag;
import com.sparta.backend.model.QArticleFolder;
import com.sparta.backend.model.QHashtag;
import com.sparta.backend.responseDto.MemberArticleFolderHashtagInfoDto;
import com.sparta.backend.responseDto.MemberHashtagInfoDto;
import com.sparta.backend.responseDto.QMemberArticleFolderHashtagInfoDto;
import com.sparta.backend.responseDto.QMemberHashtagInfoDto;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.querydsl.core.group.GroupBy.*;
import static com.sparta.backend.model.QArticleFolder.*;
import static com.sparta.backend.model.QHashtag.*;

public class MemberRepositoryImpl implements MemberRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public MemberHashtagInfoDto memberHashtagInfo(Long memberId) {
        return
                queryFactory
                        .select(new QMemberHashtagInfoDto(
                                hashtag.hashtag1.as("memberHashtag1"),
                                hashtag.hashtag2.as("memberHashtag2"),
                                hashtag.hashtag3.as("memberHashtag3")
                        ))
                        .from(hashtag)
                        .where(hashtag.member.id.eq(memberId))
                        .fetchOne();
    }

    @Override
    public Optional<MemberArticleFolderHashtagInfoDto> memberArticleFolderHashtagInfo(Long memberId) {
        return
                Optional.ofNullable(queryFactory
                        .select(new QMemberArticleFolderHashtagInfoDto(
                                articleFolder.folderHashtag1,
                                articleFolder.folderHashtag2,
                                articleFolder.folderHashtag3
                        ))
                        .from(articleFolder)
                        .where(memberIdEq(memberId), mustNotHide(articleFolder.id), mustNotDeleteable(articleFolder.id))
                        .orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
                        .limit(1)
                        .fetchOne());
    }


    private BooleanExpression memberIdEq(Long memberId) {
        return memberId != null ? articleFolder.member.id.eq(memberId) : null;
    }

    private BooleanExpression mustNotHide(NumberPath<Long> folderId) {
        return folderId != null ? articleFolder.folderHide.eq(false) : null;
    }

    private BooleanExpression mustNotDeleteable(NumberPath<Long> folderId) {
        return folderId != null ? articleFolder.deleteable.eq(true) : null;
    }
}


