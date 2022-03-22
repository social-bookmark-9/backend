package com.sparta.backend.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReminder is a Querydsl query type for Reminder
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QReminder extends EntityPathBase<Reminder> {

    private static final long serialVersionUID = 531856823L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReminder reminder = new QReminder("reminder");

    public final QArticle article;

    public final NumberPath<Integer> buttonDate = createNumber("buttonDate", Integer.class);

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imgOg = createString("imgOg");

    public final StringPath memberName = createString("memberName");

    public final DatePath<java.time.LocalDate> sendDate = createDate("sendDate", java.time.LocalDate.class);

    public final StringPath titleOg = createString("titleOg");

    public final StringPath url = createString("url");

    public QReminder(String variable) {
        this(Reminder.class, forVariable(variable), INITS);
    }

    public QReminder(Path<? extends Reminder> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReminder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReminder(PathMetadata metadata, PathInits inits) {
        this(Reminder.class, metadata, inits);
    }

    public QReminder(Class<? extends Reminder> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.article = inits.isInitialized("article") ? new QArticle(forProperty("article"), inits.get("article")) : null;
    }

}

