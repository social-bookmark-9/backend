package com.sparta.backend.requestDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class ReminderRequestDto {

    @NotBlank(message = "아티클의 제목이 있어야 합니다.")
    private String titleOg;

    @NotBlank(message = "언제 받을지 설정해야 합니다.")
    private int buttonDate;

    @NotBlank(message = "아티클의 주소가 필요합니다.")
    private String url;

    @NotBlank(message = "아티클의 ID가 필요합니다")
    private long articleId;
}
