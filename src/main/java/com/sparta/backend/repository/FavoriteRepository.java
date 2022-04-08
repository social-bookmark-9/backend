package com.sparta.backend.repository;

import com.sparta.backend.model.ArticleFolder;
import com.sparta.backend.model.Favorite;
import com.sparta.backend.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    Optional<Favorite> findByMemberAndArticleFolder(Member member, ArticleFolder articleFolder);

    @Query("select f.articleFolder.id from Favorite f where f.member.id = :memberId")
    List<Long> memberFavoriteFolderList(@Param("memberId") Long memberId);
}
