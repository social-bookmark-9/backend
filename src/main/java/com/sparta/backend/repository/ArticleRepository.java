package com.sparta.backend.repository;

import com.sparta.backend.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findArticlesByHashtag_Hashtag1(String mainHashtag);
}
