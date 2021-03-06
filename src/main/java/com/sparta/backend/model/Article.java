package com.sparta.backend.model;

import com.sparta.backend.requestDto.ArticleReviewRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "article")
public class Article extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "title_og")
    private String titleOg;

    @Column(name = "img_og")
    private String imgOg;

    @Column(name = "content_og")
    private String contentOg;

    @Column(name = "review")
    private String review;

    @Column(name = "review_hide")
    private Boolean reviewHide;

    @Column(name = "read_count", nullable = false)
    private Integer readCount;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    private Hashtag hashtag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_folder_id", nullable = false)
    private ArticleFolder articleFolder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "article", cascade = CascadeType.ALL)
    private Reminder reminder;

    // 리뷰 수정
    public String updateArticleReview(ArticleReviewRequestDto requestDto) {
        return this.review = requestDto.getReview();
    }

    // 리뷰 공개여부 수정
    public Boolean updateArticleReviewHide(boolean reviewHide) {
        return this.reviewHide = !reviewHide;
    }

    // 읽은 횟수 수정
    public void addReadCount() {
        this.readCount += 1;
    }

    // 아티클 타이틀 수정
    public String updateTitle(String titleOg) { return this.titleOg = titleOg; }

    // 아티클 폴더 이동
    public void updateArticleFolder(ArticleFolder articleFolder) { this.articleFolder = articleFolder;}

    // 연관관계 편의 메소드
    public void setArticleFolder(ArticleFolder articleFolder) {
        articleFolder.getArticles().add(this);
    }

    // 연관관계 편의 메소드
    public void setReminder(Reminder reminder) {
        this.reminder = reminder;
    }

    @Builder
    public Article(String url, String titleOg, String imgOg, String contentOg,
                   String review, Boolean reviewHide, int readCount,
                   Hashtag hashtag, ArticleFolder articleFolder, Member member) {
        this.url = url;
        this.titleOg = titleOg;
        this.imgOg = imgOg;
        this.contentOg = contentOg;
        this.review = review;
        this.reviewHide = reviewHide;
        this.readCount = readCount;
        this.hashtag = hashtag;
        this.articleFolder = articleFolder;
        this.member = member;
    }
}
