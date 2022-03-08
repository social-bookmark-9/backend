package com.sparta.backend.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_name", nullable = false, unique = true)
    private String memberName;

    @Column(name = "member_comment")
    private String memberComment;

    @Column(name = "social_url")
    private String socialUrl;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "expired_date", nullable = false)
    private Long expiredDate;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "hashtag_id", nullable = false)
    private Hashtag hashtag;

    // 테스트용
    @Builder
    public Member (String memberName, String email, String password, Long expiredDate,Hashtag hashtag, Favorite favorite) {
        this.memberName = memberName;
        this.email = email;
        this.password = password;
        this.expiredDate = expiredDate;
        this.hashtag = hashtag;
    }
}
