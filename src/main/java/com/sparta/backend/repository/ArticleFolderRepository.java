package com.sparta.backend.repository;

import com.sparta.backend.model.ArticleFolder;
import com.sparta.backend.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleFolderRepository extends JpaRepository<ArticleFolder, Long>, ArticleFolderRepositoryCustom {

    ArticleFolder findArticleFolderByArticleFolderNameAndMember(String currentArticleFolderName, Member member);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE ArticleFolder articleFolder SET articleFolder.articleFolderName = :articleFolderName WHERE articleFolder.id = :id")
    void updateArticleFolderName(@Param("articleFolderName") String articleFolderName, @Param("id") Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE ArticleFolder articleFolder SET articleFolder.folderHashtag1 = :hashtag1, articleFolder.folderHashtag2 = :hashtag2, articleFolder.folderHashtag3 = :hashtag3 WHERE articleFolder.id = :id")
    void updateArticleFolderHashtag(@Param("hashtag1") String hashtag1, @Param("hashtag2") String hashtag2, @Param("hashtag3") String hashtag3, @Param("id") Long id);

    // 비로그인시 메인페이지
    List<ArticleFolder> findTop50ByOrderByLikeCountDesc();

    // 로그인시 메인페이지
    List<ArticleFolder> findArticleFoldersByFolderHide(boolean folderHide);
}