package com.sparta.backend.repository;

import com.sparta.backend.model.ArticleFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleFolderRepository extends JpaRepository<ArticleFolder, Long> {

    @Modifying(clearAutomatically = true)
    @Query("UPDATE ArticleFolder af SET af.articleFolderName = :articleFolderName WHERE af.id = :id")
    void updateArticleFolderTitle(String articleFolderName, Long id);

    // 추후 폴더 안 아티클 수정 시 통합 시도

}