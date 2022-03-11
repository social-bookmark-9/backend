package com.sparta.backend.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleFolder extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artice_folder_id")
    private Long id;

    @Column(name = "artice_folder_name", nullable = false)
    private String articleFolderName;

    @Column(name = "deleteable", nullable = false)
    private Boolean deleteable;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    @OneToMany(mappedBy = "articleFolder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Article> articles = new ArrayList<>();

    // 테스트용
    @Builder
    public ArticleFolder(String articleFolderName, Boolean deleteable, Member member) {
        this.articleFolderName = articleFolderName;
        this.deleteable = deleteable;
        this.member = member;
    }
}
