package com.sparta.backend.repository;

import com.sparta.backend.model.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Override
    @EntityGraph(attributePaths = {"hashtag"})
    Optional<Member> findById(@NotNull Long id);

    Optional<Member> findMemberByKakaoId(String kakaoId);
    
    // 메인 페이지 검색용
    @EntityGraph(attributePaths = {"hashtag", "memberRoles"})
    List<Member> findMembersByHashtag_Hashtag1(String hashtag);

    // 중복 멤버네임 검사
    boolean existsMemberByMemberName(String memberName);

    // 회원가입시 카카오id로 중복유저 검사
    boolean existsMemberByKakaoId(String kakaoId);

    // 테스트용
    @EntityGraph(attributePaths = {"hashtag", "memberRoles"})
    List<Member> findAll();
}
