package com.sparta.backend.repository;

import com.sparta.backend.model.Article;
import com.sparta.backend.model.Member;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findArticlesByHashtag_Hashtag1(String mainHashtag);
    List<Article> findAllByMember(Member member);
    // 메인페이지 태그 검색용 / 한달 내에 생성된 것들.
    List<Article> findArticlesByHashtag_Hashtag1AndCreatedAtBetween(String mainHashtag, LocalDateTime start, LocalDateTime end);
}
