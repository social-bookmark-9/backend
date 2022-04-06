package com.sparta.backend.repositorycustom;

import java.util.List;

public interface MemberRepositoryCustom {
    List<String> memberHashtagInfo(Long memberId);
}
