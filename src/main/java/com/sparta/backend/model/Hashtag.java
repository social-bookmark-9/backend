package com.sparta.backend.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "hashtag",
        indexes = {
                @Index(name = "h1_idx", columnList = "hashtag_1"),
                @Index(name = "mid_h1_idx", columnList = "member_id, hashtag_1"),
                @Index(name = "art_h1_idx", columnList = "article_id, hashtag_1")
        })
public class Hashtag extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    private Long id;

    @Column(name = "hashtag_1", nullable = false)
    private String hashtag1;

    @Column(name = "hashtag_2")
    private String hashtag2;

    @Column(name = "hashtag_3")
    private String hashtag3;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article = null;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member = null;

    // 해시태그 설정
    public void setHashtag(String hashtag1, String hashtag2, String hashtag3) {
        this.hashtag1 = hashtag1;
        this.hashtag2 = hashtag2;
        this.hashtag3 = hashtag3;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
    public void setMember(Member member) {
        this.member = member;
    }

    // 테스트용
    @Builder
    public Hashtag(String hashtag1, String hashtag2, String hashtag3) {
        this.hashtag1 = hashtag1;
        this.hashtag2 = hashtag2;
        this.hashtag3 = hashtag3;
    }
}
