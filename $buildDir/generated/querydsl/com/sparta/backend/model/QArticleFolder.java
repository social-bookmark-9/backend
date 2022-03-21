package com.sparta.backend.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QArticleFolder is a Querydsl query type for ArticleFolder
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QArticleFolder extends EntityPathBase<ArticleFolder> {

    private static final long serialVersionUID = 2032044767L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QArticleFolder articleFolder = new QArticleFolder("articleFolder");

    public final QTimestamped _super = new QTimestamped(this);

    public final StringPath articleFolderName = createString("articleFolderName");

    public final ListPath<Article, QArticle> articles = this.<Article, QArticle>createList("articles", Article.class, QArticle.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final BooleanPath deleteable = createBoolean("deleteable");

    public final BooleanPath folderHide = createBoolean("folderHide");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public QArticleFolder(String variable) {
        this(ArticleFolder.class, forVariable(variable), INITS);
    }

    public QArticleFolder(Path<? extends ArticleFolder> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QArticleFolder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QArticleFolder(PathMetadata metadata, PathInits inits) {
        this(ArticleFolder.class, metadata, inits);
    }

    public QArticleFolder(Class<? extends ArticleFolder> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member"), inits.get("member")) : null;
    }

}

