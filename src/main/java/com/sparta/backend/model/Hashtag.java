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
    private Long id;

    @Column(name = "first_hashtag", nullable = false)
    private String firstHashtag;

    @Column(name = "second_hashtag")
    private String secondHashtag;

    @Column(name = "third_hashtag")
    private String thirdHashtag;

    // 테스트용
    @Builder
    public Hashtag(String firstHashtag, String secondHashtag, String thirdHashtag) {
        this.firstHashtag = firstHashtag;
        this.secondHashtag = secondHashtag;
        this.thirdHashtag = thirdHashtag;
    }

}
