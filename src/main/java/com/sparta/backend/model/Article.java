package com.sparta.backend.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "title_og", nullable = false)
    private String titleOg;

    @Column(name = "img_og", nullable = false)
    private String imgOg;

    @Column(name = "review")
    private String review;

    @Column(name = "review_hide")
    private boolean reviewHide;

    @Column(name = "read_count")
    private int readCount;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "hashtag_id", nullable = false)
    private Hashtag hashtag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_folder_id", nullable = false)
    private ArticleFolder articleFolder;

    // 연관관계 편의 메소드
    public void setArticleFolder(ArticleFolder articleFolder) {
        this.articleFolder = articleFolder;
        articleFolder.getArticles().add(this);
    }

    // 테스트용
    @Builder
    public Article(String url, String titleOg, String imgOg, Hashtag hashtag, ArticleFolder articleFolder) {
        this.url = url;
        this.titleOg = titleOg;
        this.imgOg = imgOg;
        this.hashtag = hashtag;
        this.articleFolder = articleFolder;
    }

    @Builder(builderMethodName = "createArticleDtoBuilder")
    public Article(String url, String titleOg, String imgOg,
                   String review, Boolean reviewHide, int readCount,
                   Hashtag hashtag, ArticleFolder articleFolder) {
        this.url = url;
        this.titleOg = titleOg;
        this.imgOg = imgOg;
        this.review = review;
        this.reviewHide = reviewHide;
        this.readCount = readCount;
        this.hashtag = hashtag;
        this.articleFolder = articleFolder;
    }
}
