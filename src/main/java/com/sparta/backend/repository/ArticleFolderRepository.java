package com.sparta.backend.repository;

import com.sparta.backend.model.ArticleFolder;
import com.sparta.backend.model.Member;
import com.sparta.backend.repositorycustom.ArticleFolderRepositoryCustom;
import com.sparta.backend.repositorycustom.ArticleFolderRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleFolderRepository extends JpaRepository<ArticleFolder, Long>, ArticleFolderRepositoryCustom {

    ArticleFolder findArticleFolderByArticleFolderNameAndMember(String currentArticleFolderName, Member member);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE ArticleFolder articleFolder SET articleFolder.articleFolderName = :articleFolderName WHERE articleFolder.id = :id")
    void updateArticleFolderName(@Param("articleFolderName") String articleFolderName, @Param("id") Long id);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE ArticleFolder articleFolder SET articleFolder.folderHashtag1 = :hashtag1, articleFolder.folderHashtag2 = :hashtag2, articleFolder.folderHashtag3 = :hashtag3 WHERE articleFolder.id = :id")
    void updateArticleFolderHashtag(@Param("hashtag1") String hashtag1, @Param("hashtag2") String hashtag2, @Param("hashtag3") String hashtag3, @Param("id") Long id);

    // 검색페이지 아티클 폴더 검색용 (기존)
    Page<ArticleFolder> findArticleFoldersByFolderHideAndFolderHashtag1AndArticleFolderNameContains(boolean folderHide, String hashtag, String articleFolderName, Pageable pageable);
    // 검색페이지 아티클 폴더 검색용 ( hashtag == null )
    Page<ArticleFolder> findArticleFoldersByFolderHideAndArticleFolderNameContains(boolean folderHide, String articleFolderName, Pageable pageable);
    // 검색페이지 아티클 폴더 검색용 ( articleFolderName == null )
    Page<ArticleFolder> findArticleFoldersByFolderHideAndFolderHashtag1(boolean folderHide, String hashtag, Pageable pageable);
    // 검색페이지 아티클 폴더 검색용 ( hashtag == null && articleFolderName == null )
    Page<ArticleFolder> findArticleFoldersByFolderHide(boolean folderHide, Pageable pageable);
}