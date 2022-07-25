package com.sparta.backend.repositorycustom;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.backend.model.QMember;
import com.sparta.backend.responseDto.*;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
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
                    .on(articleFolder.member.id.eq(hashtag.member.id), mustNotHide(), mustNotDeleteable())
                    .where(hashtag.member.id.eq(memberId))
                    .orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc())
                    .limit(1)
                    .fetchOne();
    }

    @Override
    public List<RecommendedMemberResponseDto> mainPageMemberLogin(Long memberId, List<String> hashtagList) {
        Double tlkAvg = queryFactory
                .select(member.totalLikeCount.avg())
                .from(member)
                .where(member.totalLikeCount.gt(0))
                .fetchOne();

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
                    .leftJoin(hashtag)
                    .on(hashtag.hashtag1.in(hashtagList))
                    .where(member.totalLikeCount.gt(tlkAvg), member.id.eq(hashtag.member.id), member.id.ne(memberId))
                    .orderBy(member.totalLikeCount.desc())
                    .limit(50)
                    .fetch();

//        List<Long> memberIds = queryFactory
//                .select(member.id)
//                .from(member)
//                .leftJoin(hashtag)
//                .on(hashtag.hashtag1.in(hashtagList))
//                .where(member.totalLikeCount.gt(tlkAvg), member.id.eq(hashtag.member.id), member.id.ne(memberId))
//                .orderBy(member.totalLikeCount.desc())
//                .limit(50)
//                .fetch();
//        return
//            queryFactory
//                    .select(new QRecommendedMemberResponseDto(
//                            member.id.as("memberId"),
//                            member.profileImage.as("profileImage"),
//                            member.memberComment.as("memberComment"),
//                            member.memberName.as("memberName"),
//                            member.totalLikeCount.as("likeCount"),
//                            hashtag.hashtag1.as("hashtag1"),
//                            hashtag.hashtag2.as("hashtag2"),
//                            hashtag.hashtag3.as("hashtag3")
//                    ))
//                    .from(hashtag)
//                    .leftJoin(member)
//                    .on(member.id.eq(hashtag.member.id))
//                    .where(member.id.in(memberIds))
//                    .fetch();
    }

    @Override
    public List<RecommendedMemberResponseDto> mainPageMemberNonLogin(String hashTag) {
        Double tlkAvg = queryFactory
                .select(member.totalLikeCount.avg())
                .from(member)
                .where(member.totalLikeCount.gt(0))
                .fetchOne();

        List<Long> memberIds = queryFactory
                .select(hashtag.member.id)
                .from(hashtag)
                .join(member)
                .on(member.id.eq(hashtag.member.id), member.totalLikeCount.gt(tlkAvg))
                .where(hashtag.hashtag1.eq(hashTag))
                .orderBy(member.totalLikeCount.desc())
                .limit(50)
                .fetch();

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
                    .where(member.id.in(memberIds))
                    .fetch();
    }

    private BooleanExpression mustNotHide() {
        return articleFolder.folderHide.eq(false);
    }

    private BooleanExpression mustNotDeleteable() {
        return articleFolder.deleteable.eq(true);
    }
}


