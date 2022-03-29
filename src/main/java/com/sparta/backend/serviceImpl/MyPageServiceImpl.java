package com.sparta.backend.serviceImpl;

import com.sparta.backend.exception.EntityNotFoundException;
import com.sparta.backend.model.ArticleFolder;
import com.sparta.backend.model.Member;
import com.sparta.backend.repository.MemberRepository;
import com.sparta.backend.responseDto.ArticleFolderListResponseDto;
import com.sparta.backend.responseDto.MemberInfoResponseDto;
import com.sparta.backend.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {

    private final MemberRepository memberRepository;

    /**
     * 내 마이페이지용 사용자 정보 조회
     * @param member
     * @return MemberInfoDto
     */
    @Override
    public MemberInfoResponseDto getMyMemberInfo(Member member) {
        return MemberInfoResponseDto.of(member);
    }

    /**
     * 다른 사람의 마이페이지용 사용자 정보 조회
     * @param memberId
     * @return MemberInfoDto
     */
    @Override
    public MemberInfoResponseDto getOtherMemberInfo(Long memberId) {
        Member findMember = getMember(memberId);

        return MemberInfoResponseDto.of(findMember);
    }

    /**
     * 내 마이페이지용 아티클 폴더 조회
     * @param member
     * @return List<ArticleFolderListDto>
     */
    @Override
    public List<ArticleFolderListResponseDto> getMyArticleFolderList(Member member) {
        Member findMember = getMember(member.getId());
        List<ArticleFolder> myArticleFolders = findMember.getArticleFolders();

        return getArticleFolderListDtoList(myArticleFolders);
    }

    /**
     * 다른 사람의 마이페이지용 아티클 폴더 조회
     * @param memberId
     * @return List<ArticleFolderListDto>
     */
    @Override
    public List<ArticleFolderListResponseDto> getOtherArticleFolderList(Long memberId) {
        Member findMember = getMember(memberId);
        List<ArticleFolder> otherArticleFolders = findMember.getArticleFolders();

        return getArticleFolderListDtoList(otherArticleFolders);
    }

    private List<ArticleFolderListResponseDto> getArticleFolderListDtoList(List<ArticleFolder> articleFolders) {
        List<ArticleFolderListResponseDto> articleFolderListDtoListResponse = new ArrayList<>();

        for (ArticleFolder articleFolder : articleFolders) {
            if (articleFolder.getArticles().isEmpty()) {
                ArticleFolderListResponseDto noArticlesInFolder = ArticleFolderListResponseDto.of(articleFolder);
                articleFolderListDtoListResponse.add(noArticlesInFolder);
            } else {
                ArticleFolderListResponseDto articlesInFolder = ArticleFolderListResponseDto.of(articleFolder, articleFolder.getArticles());
                articleFolderListDtoListResponse.add(articlesInFolder);
            }
        }

        return articleFolderListDtoListResponse;
    }

    /**
     * Member 조회
     * @param id
     */
    private Member getMember(Long id) {
        Optional<Member> member = memberRepository.findById(id);
        if (member.isPresent()) {
            return member.get();
        } else throw new EntityNotFoundException("존재하지 않는 회원");
    }
}
