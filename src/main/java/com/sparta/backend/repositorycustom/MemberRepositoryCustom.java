package com.sparta.backend.repositorycustom;

import com.sparta.backend.responseDto.MemberArticleFolderHashtagInfoDto;
import com.sparta.backend.responseDto.MemberHashtagInfoDto;

import java.util.List;
import java.util.Optional;

public interface MemberRepositoryCustom {
    MemberHashtagInfoDto memberHashtagInfo(Long memberId);
    Optional<MemberArticleFolderHashtagInfoDto> memberArticleFolderHashtagInfo(Long memberId);
}
