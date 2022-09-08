package com.sparta.backend.service;

import com.sparta.backend.model.Member;
import com.sparta.backend.responseDto.*;


public interface MainPageService {
    MainPageResponseDto mainPageLoginVer(Member member);
    MainPageResponseDto mainPageNonLoginVer();
}
