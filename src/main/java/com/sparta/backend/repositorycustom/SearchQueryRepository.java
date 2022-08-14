package com.sparta.backend.repositorycustom;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.backend.model.QHashtag;
import com.sparta.backend.responseDto.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import javax.persistence.EntityManager;
import java.util.List;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;
import static com.sparta.backend.model.QArticle.article;
import static com.sparta.backend.model.QArticleFolder.*;
import static com.sparta.backend.model.QHashtag.hashtag;

@Repository
public class SearchQueryRepository {

    private final JPAQueryFactory queryFactory;

    public SearchQueryRepository(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private static class DataResultSlice {
        public static <T> Slice<T> toSlice(List<T> contents, Pageable pageable) {
            boolean hasNext = isContentSizeGreaterThanPageSize(contents, pageable);
            return new SliceImpl<>(hasNext ? subListLastContent(contents, pageable) : contents, pageable, hasNext);
        }

        // 다음 페이지 있는지 확인
        private static <T> boolean isContentSizeGreaterThanPageSize(List<T> content, Pageable pageable) {
            return pageable.isPaged() && content.size() > pageable.getPageSize();
        }

        // 데이터 1개 빼고 반환
        private static <T> List<T> subListLastContent(List<T> content, Pageable pageable) {
            return content.subList(0, pageable.getPageSize());
        }
    }

    // only folders
    public Slice<MainAndSearchPageArticleFolderResponseDto> searchOnlyArticleFolders(String hashtag, String keyword, Pageable pageable) {
        List<Long> ids = queryFactory
                .select(articleFolder.id)
                .from(articleFolder)
                .where(articleFolderKeywordMatch(keyword), articleFolderHashtagMatch(hashtag), articleFolder.folderHide.eq(false))
                .limit(pageable.getPageSize() + 1)
                .offset(pageable.getOffset())
                .fetch();

        if (CollectionUtils.isEmpty(ids)) {
            return null;
        }

        List<MainAndSearchPageArticleFolderResponseDto> results = queryFactory
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
                                        articleFolder.likeCount.as("likeCount"),
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

        return DataResultSlice.toSlice(results, pageable);
    }

    // only articles
    public Slice<SearchPageArticleResponseDto> searchOnlyArticles(String hashTag, String keyword, Pageable pageable) {
        List<SearchPageArticleResponseDto> results = queryFactory
                .select(new QSearchPageArticleResponseDto(
                        article.id.as("articleId"),
                        article.titleOg.as("titleOg"),
                        article.imgOg.as("imgOg"),
                        article.contentOg.as("contentOg"),
                        hashtag.hashtag1.as("hashtag1"),
                        hashtag.hashtag2.as("hashtag2"),
                        hashtag.hashtag3.as("hashtag3"))
                )
                .from(article)
                .join(articleFolder)
                .on(articleFolder.id.eq(article.articleFolder.id), articleFolder.folderHide.eq(false))
                .join(hashtag)
                .on(hashtag.article.id.eq(article.id))
                .where(articleKeywordMatch(keyword), hashtagMatch(hashTag))
                .limit(pageable.getPageSize() + 1)
                .offset(pageable.getOffset())
                .fetch();

        return DataResultSlice.toSlice(results, pageable);
    }

    // folders + articles(새로운 Dto 만들기)



    private BooleanExpression articleFolderKeywordMatch(String keyword) {
        if (keyword == null || keyword.isEmpty()) return null;

        NumberTemplate<Double> booleanTemplate = Expressions.numberTemplate(Double.class,
                "function('match(?)',{0},{1})", articleFolder.articleFolderName, keyword);

        return booleanTemplate.gt(0);
    }

    private BooleanExpression articleKeywordMatch(String keyword) {
        if (keyword == null || keyword.isEmpty()) return null;

        NumberTemplate<Double> booleanTemplate = Expressions.numberTemplate(Double.class,
                "function('match(?,?)',{0},{1},{2})", article.titleOg, article.contentOg, keyword);

        return booleanTemplate.gt(0);
    }

    private BooleanExpression articleFolderHashtagMatch(String hashtag) {
        if (hashtag == null || hashtag.isEmpty()) return null;

        return articleFolder.folderHashtag1.eq(hashtag);
    }

    private BooleanExpression hashtagMatch(String hashTag) {
        if (hashTag == null || hashTag.isEmpty()) return null;

        return hashtag.hashtag1.eq(hashTag);
    }
}










