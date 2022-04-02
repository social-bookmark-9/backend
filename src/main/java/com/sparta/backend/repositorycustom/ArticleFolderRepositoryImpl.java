package com.sparta.backend.repositorycustom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.backend.model.ArticleFolder;
import com.sparta.backend.model.QArticleFolder;

import javax.persistence.EntityManager;
import java.util.List;

public class ArticleFolderRepositoryImpl implements ArticleFolderRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public ArticleFolderRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<ArticleFolder> findAllArticleFolder() {
        QArticleFolder articleFolder = QArticleFolder.articleFolder;

        return queryFactory
                .selectFrom(articleFolder)
                .fetch();
    }
}
