package com.sparta.backend.repository;

import com.sparta.backend.model.ArticleFolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleFolderRepository extends JpaRepository<ArticleFolder, Long> {
}
