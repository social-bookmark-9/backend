package com.sparta.backend.model;

import com.sparta.backend.model.ArticleFolder;
import com.sparta.backend.model.Hashtag;
import com.sparta.backend.model.Timestamped;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    private long id;

    @Column(name = "kakao_id", unique = true)
    private String kakaoId;

    @Column(name = "member_name", unique = true)
    private String memberName;

    @Column(name = "member_comment")
    private String memberComment;

    @Column(name = "instagram_url")
    private String instagramUrl;

    @Column(name = "github_url")
    private String githubUrl;

    @Column(name = "brunch_url")
    private String brunchUrl;

    @Column(name = "blog_url")
    private String blogUrl;

    @Column(name = "website_url")
    private String websiteUrl;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "profile_image")
    private String profileImage;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private Hashtag hashtag;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<ArticleFolder> articleFolders = new ArrayList<>();

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
                   Hashtag hashtag, String kakaoId, List<String> memberRoles, String profileImage) {
        this.memberName = memberName;
        this.email = email;
        this.profileImage = profileImage;
        this.password = password;
        this.hashtag = hashtag;
        this.kakaoId = kakaoId;
        this.memberRoles = memberRoles;
    }

    // 프로필 닉네임 수정하기
    public void editMemberName(String memberName) {
        this.memberName = memberName;
    }

    // 프로필 사진 수정하기
    public void editProfileImageUrl(String profileImageUrl) {
        this.profileImage = profileImageUrl;
    }

    // 프로필 자기소개 수정하기
    public void editStatusMessage(String userDesc) {
        this.memberComment = userDesc;
    }

    // 프로필 SNS URL 수정하기
    public void setInstagramUrl(String instagramUrl) {
        this.instagramUrl = instagramUrl;
    }
    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
    }
    public void setBrunchUrl(String brunchUrl) {
        this.brunchUrl = brunchUrl;
    }
    public void setBlogUrl(String blogUrl) {
        this.blogUrl = blogUrl;
    }
    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }
}
