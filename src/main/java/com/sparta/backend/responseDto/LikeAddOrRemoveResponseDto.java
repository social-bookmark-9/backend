package com.sparta.backend.responseDto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class LikeAddOrRemoveResponseDto {
    private boolean likeStatus;
}
