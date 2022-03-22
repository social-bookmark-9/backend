package com.sparta.backend.responseDto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReminderResponseDto {
    private Long articleId;
    private String imgOg;
    private String titleOg;
    private String hashtag1;
    private String hashtag2;
    private String hashtag3;
}
