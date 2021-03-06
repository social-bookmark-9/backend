package com.sparta.backend.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "article_folder")
public class ArticleFolder extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_folder_id")
    private Long id;

    @Column(name = "article_folder_name", nullable = false)
    private String articleFolderName;

    @Column(name = "deleteable", nullable = false)
    private Boolean deleteable;

    @Column(name = "folder_hide", nullable = false)
    private Boolean folderHide;

    @Column(name = "like_count", nullable = false)
    private int likeCount;

    @Column(name = "folder_hashtag1")
    private String folderHashtag1;

    @Column(name = "folder_hashtag2")
    private String folderHashtag2;

    @Column(name = "folder_hashtag3")
    private String folderHashtag3;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "articleFolder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Article> articles = new ArrayList<>();

    @Builder
    public ArticleFolder(boolean folderHide, boolean deleteable, String articleFolderName, int likeCount, Member member) {
        this.folderHide = folderHide;
        this.deleteable = deleteable;
        this.articleFolderName = articleFolderName;
        this.likeCount = likeCount;
        this.member = member;
        member.getArticleFolders().add(this);
    }

    public void increaseLikeCount(int currentLikeCount) {
        this.likeCount = ++currentLikeCount;
    }

    public void decreaseLikeCount(int currentLikeCount) {
        this.likeCount = --currentLikeCount;
    }
}
