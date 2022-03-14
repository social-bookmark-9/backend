package com.sparta.backend.responseDto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class LikeAddOrDeleteResponseDto {
    private boolean likeStatus;
}
