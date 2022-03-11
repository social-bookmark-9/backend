package com.sparta.backend.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member extends Timestamped implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "kakao_id", unique = true)
    private String kakaoId;

    @Column(name = "member_name", unique = true)
    private String memberName;

    @Column(name = "member_comment")
    private String memberComment;

    @Column(name = "social_url")
    private String socialUrl;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "expired_date", nullable = false)
    private Long expiredDate;

    @Column(name = "profile_Image")
    private String profileImage;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "hashtag_id")
    private Hashtag hashtag;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> memberRoles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.memberRoles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return kakaoId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // 테스트용
    @Builder
    public Member (String memberName, String email, String password,
                   Long expiredDate,Hashtag hashtag, String kakaoId, List<String> memberRoles, String profileImage) {
        this.memberName = memberName;
        this.email = email;
        this.profileImage = profileImage;
        this.password = password;
        this.expiredDate = expiredDate;
        this.hashtag = hashtag;
        this.kakaoId = kakaoId;
        this.memberRoles = memberRoles;
    }
}
