package com.sparta.backend.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -1702374113L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMember member = new QMember("member1");

    public final QTimestamped _super = new QTimestamped(this);

    public final ListPath<ArticleFolder, QArticleFolder> articleFolders = this.<ArticleFolder, QArticleFolder>createList("articleFolders", ArticleFolder.class, QArticleFolder.class, PathInits.DIRECT2);

    public final StringPath blogUrl = createString("blogUrl");

    public final StringPath brunchUrl = createString("brunchUrl");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath email = createString("email");

    public final StringPath githubUrl = createString("githubUrl");

    public final QHashtag hashtag;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath instagramUrl = createString("instagramUrl");

    public final StringPath kakaoId = createString("kakaoId");

    public final StringPath memberComment = createString("memberComment");

    public final StringPath memberName = createString("memberName");

    public final ListPath<String, StringPath> memberRoles = this.<String, StringPath>createList("memberRoles", String.class, StringPath.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath password = createString("password");

    public final StringPath profileImage = createString("profileImage");

    public final StringPath websiteUrl = createString("websiteUrl");

    public QMember(String variable) {
        this(Member.class, forVariable(variable), INITS);
    }

    public QMember(Path<? extends Member> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMember(PathMetadata metadata, PathInits inits) {
        this(Member.class, metadata, inits);
    }

    public QMember(Class<? extends Member> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.hashtag = inits.isInitialized("hashtag") ? new QHashtag(forProperty("hashtag"), inits.get("hashtag")) : null;
    }

}

