package com.sparta.backend.repositorycustom;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.backend.responseDto.MemberHashtagInfoDto;
import com.sparta.backend.responseDto.RecommendedMemberResponseDto;
import com.sparta.backend.responseDto.*;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.sparta.backend.model.QArticleFolder.*;
import static com.sparta.backend.model.QHashtag.*;
import static com.sparta.backend.model.QMember.*;

public class MemberRepositoryCustomImpl implements MemberRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public MemberHashtagInfoDto memberHashtagInfo(Long memberId) {
        return
                queryFactory
                    .select(new QMemberHashtagInfoDto(
                            hashtag.hashtag1.as("memberHashtag1"),
                            hashtag.hashtag2.as("memberHashtag2"),
                            hashtag.hashtag3.as("memberHashtag3"),
                            articleFolder.folderHashtag1.as("folderHashtag1"),
                            articleFolder.folderHashtag2.as("folderHashtag2"),
                            articleFolder.folderHashtag3.as("folderHashtag3")
                    ))
                    .from(hashtag)
                    .leftJoin(articleFolder)
                    .on(articleFolder.member.id.eq(hashtag.member.id), articleFolder.folderHide.eq(false), articleFolder.deleteable.eq(true))
                    .where(hashtag.member.id.eq(memberId))
                    .orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
                    .limit(1)
                    .fetchOne();
    }

    @Override
    public List<RecommendedMemberResponseDto> mainPageMemberLogin(Long memberId, List<String> hashtagList) {
        List<Long> ids = queryFactory
                .select(member.id)
                .from(member)
                .join(hashtag)
                .on(hashtag.member.id.eq(member.id))
                .where(hashtagsIn(hashtagList))
                .orderBy(member.totalLikeCount.desc())
                .limit(50)
                .fetch();

        if (ids.isEmpty()) {
            return new ArrayList<>();
        } else {
            ids.removeIf(id -> Objects.equals(id, memberId));
        }

        return
                queryFactory
                        .select(new QRecommendedMemberResponseDto(
                                member.id.as("memberId"),
                                member.profileImage.as("profileImage"),
                                member.memberComment.as("memberComment"),
                                member.memberName.as("memberName"),
                                member.totalLikeCount.as("likeCount"),
                                hashtag.hashtag1.as("hashtag1"),
                                hashtag.hashtag2.as("hashtag2"),
                                hashtag.hashtag3.as("hashtag3")
                        ))
                        .from(member)
                        .join(hashtag)
                        .on(hashtag.member.id.eq(member.id))
                        .where(member.id.in(ids))
                        .fetch();
    }

    @Override
    public List<RecommendedMemberResponseDto> mainPageMemberNonLogin(String hashTag) {
        List<Long> ids = queryFactory
                .select(member.id)
                .from(member)
                .join(hashtag)
                .on(hashtag.member.id.eq(member.id))
                .where(hashtag.hashtag1.eq(hashTag))
                .orderBy(member.totalLikeCount.desc())
                .limit(50)
                .fetch();

        if (ids.isEmpty()) {
            return new ArrayList<>();
        }

        return
                queryFactory
                        .select(new QRecommendedMemberResponseDto(
                                member.id.as("memberId"),
                                member.profileImage.as("profileImage"),
                                member.memberComment.as("memberComment"),
                                member.memberName.as("memberName"),
                                member.totalLikeCount.as("likeCount"),
                                hashtag.hashtag1.as("hashtag1"),
                                hashtag.hashtag2.as("hashtag2"),
                                hashtag.hashtag3.as("hashtag3")
                        ))
                        .from(member)
                        .join(hashtag)
                        .on(hashtag.member.id.eq(member.id))
                        .where(member.id.in(ids))
                        .fetch();
    }

    private BooleanExpression hashtagsIn(List<String> hashtagList) {
        if (hashtagList.isEmpty()) {
            return null;
        }

        return hashtag.hashtag1.in(hashtagList);
    }

}


