package com.sparta.backend.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    @OneToMany(mappedBy = "articleFolder", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Article> articles = new ArrayList<>();

    // 테스트용
    @Builder
    public ArticleFolder(Boolean deleteable, String articleFolderName, Member member) {
        this.deleteable = deleteable;
        this.articleFolderName = articleFolderName;
        this.member = member;
    }

    // 아티클 폴더에서 해당 아티클 삭제 (아티클 폴더를 수정하기 위함)
    public void deleteArticleFromArticleFolder(Article currentArticle) {
        Long currentArticleId = currentArticle.getId();
        int size = articles.size();
        for (int i = 0; i < size; i++) {
            Long articleId = articles.get(i).getId();
            if (Objects.equals(currentArticleId, articleId)) {
                articles.remove(i);
                size--;
                i--;
            }
        }
    }

}
