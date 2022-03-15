package com.sparta.backend.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "hashtag")
public class Hashtag extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hashtag_id")
    private long id;

    @Column(name = "hashtag_1", nullable = false)
    private String hashtag1;

    @Column(name = "hashtag_2")
    private String hashtag2;

    @Column(name = "hashtag_3")
    private String hashtag3;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    // 테스트용
    @Builder
    public Hashtag(String hashtag1, String hashtag2, String hashtag3, Article article) {
        this.hashtag1 = hashtag1;
        this.hashtag2 = hashtag2;
        this.hashtag3 = hashtag3;
        this.article = article;
    }
}
