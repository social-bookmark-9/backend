package com.sparta.backend.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QArticle is a Querydsl query type for Article
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QArticle extends EntityPathBase<Article> {

    private static final long serialVersionUID = 1379717905L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QArticle article = new QArticle("article");

    public final QTimestamped _super = new QTimestamped(this);

    public final QArticleFolder articleFolder;

    public final StringPath contentOg = createString("contentOg");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final QHashtag hashtag;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imgOg = createString("imgOg");

    public final QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final NumberPath<Integer> readCount = createNumber("readCount", Integer.class);

    public final QReminder reminder;

    public final StringPath review = createString("review");

    public final BooleanPath reviewHide = createBoolean("reviewHide");

    public final StringPath titleOg = createString("titleOg");

    public final StringPath url = createString("url");

    public QArticle(String variable) {
        this(Article.class, forVariable(variable), INITS);
    }

    public QArticle(Path<? extends Article> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QArticle(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QArticle(PathMetadata metadata, PathInits inits) {
        this(Article.class, metadata, inits);
    }

    public QArticle(Class<? extends Article> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.articleFolder = inits.isInitialized("articleFolder") ? new QArticleFolder(forProperty("articleFolder"), inits.get("articleFolder")) : null;
        this.hashtag = inits.isInitialized("hashtag") ? new QHashtag(forProperty("hashtag"), inits.get("hashtag")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
        this.reminder = inits.isInitialized("reminder") ? new QReminder(forProperty("reminder"), inits.get("reminder")) : null;
    }

}

