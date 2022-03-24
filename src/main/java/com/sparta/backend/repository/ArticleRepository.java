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
    List<Article> findArticlesByIdNotAndHashtag_Hashtag1AndArticleFolder_FolderHide(Long id ,String mainHashtag, boolean folderHide);
    List<Article> findAllByMember(Member member);
    // 메인페이지 아티클 검색용(Folder Hide 반영)
    List<Article> findArticlesByHashtag_Hashtag1AndArticleFolder_FolderHideAndCreatedAtBetween(String mainHashtag, boolean folderHide, LocalDateTime start, LocalDateTime end);
}
