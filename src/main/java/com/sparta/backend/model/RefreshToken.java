package com.sparta.backend.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "refresh_token")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken extends Timestamped {

    @Id
    @Column(name = "refresh_token_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "refresh_token_key", nullable = false)
    private String key;

    @Column(name = "token", nullable = false)
    private String token;

    public RefreshToken updateToken(String token){
        this.token = token;
        return this;
    }

    @Builder
    public RefreshToken(String key, String token){
        this.key = key;
        this.token = token;
    }
}
