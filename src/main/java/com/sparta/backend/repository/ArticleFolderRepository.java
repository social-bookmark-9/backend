package com.sparta.backend.repository;

import com.sparta.backend.model.ArticleFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleFolderRepository extends JpaRepository<ArticleFolder, Long> {

    ArticleFolder findArticleFolderByArticleFolderName(String currentArticleFolderName);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE ArticleFolder articleFolder SET articleFolder.articleFolderName = :articleFolderName WHERE articleFolder.id = :id")
    void updateArticleFolderName(@Param("articleFolderName") String articleFolderName, @Param("id") long id);
}