package com.sparta.backend.serviceImpl;

import com.sparta.backend.exception.EntityNotFoundException;
import com.sparta.backend.model.ArticleFolder;
import com.sparta.backend.model.Member;
import com.sparta.backend.repository.MemberRepository;
import com.sparta.backend.responseDto.ArticleFolderListDto;
import com.sparta.backend.responseDto.MemberInfoDto;
import com.sparta.backend.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService {

    private final MemberRepository memberRepository;

    /**
     * 내 마이페이지용 사용자 정보 조회
     * @param member
     * @return MemberInfoDto
     */
    @Override
    public MemberInfoDto getMyMemberInfo(Member member) {
        return MemberInfoDto.of(member);
    }

    /**
     * 다른 사람의 마이페이지용 사용자 정보 조회
     * @param memberId
     * @return MemberInfoDto
     */
    @Override
    public MemberInfoDto getOtherMemberInfo(long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
            member.orElseThrow(() -> new EntityNotFoundException("해당 유저 없음"));

        return MemberInfoDto.of(member.get());
    }

    /**
     * 내 마이페이지용 아티클 폴더 조회
     * @param member
     * @return List<ArticleFolderListDto>
     */
    @Override
    public List<ArticleFolderListDto> getMyArticleFolderList(Member member) {
        List<ArticleFolder> myArticleFolders = member.getArticleFolders();

        return getArticleFolderListDtoList(myArticleFolders);
    }

    /**
     * 다른 사람의 마이페이지용 아티클 폴더 조회
     * @param memberId
     * @return List<ArticleFolderListDto>
     */
    @Override
    public List<ArticleFolderListDto> getOtherArticleFolderList(long memberId) {
        Optional<Member> member = memberRepository.findById(memberId);
            member.orElseThrow(() -> new EntityNotFoundException("해당 유저 없음"));

        List<ArticleFolder> otherArticleFolders = member.get().getArticleFolders();

        return getArticleFolderListDtoList(otherArticleFolders);
    }

    private List<ArticleFolderListDto> getArticleFolderListDtoList(List<ArticleFolder> articleFolders) {
        List<ArticleFolderListDto> articleFolderListDtoList = new ArrayList<>();

        for (ArticleFolder articleFolder : articleFolders) {
            if (articleFolder.getArticles().isEmpty()) {
                ArticleFolderListDto articleFolderListDto = ArticleFolderListDto.of(articleFolder);
                articleFolderListDtoList.add(articleFolderListDto);
            }
            ArticleFolderListDto articleFolderListDto = ArticleFolderListDto.of(articleFolder, articleFolder.getArticles());
            articleFolderListDtoList.add(articleFolderListDto);
        }

        return articleFolderListDtoList;
    }
}
