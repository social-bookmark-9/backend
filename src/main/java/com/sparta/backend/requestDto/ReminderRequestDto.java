package com.sparta.backend.requestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReminderRequestDto {

    @NotBlank(message = "아티클의 제목이 있어야 합니다.")
    private String titleOg;

    @NotBlank(message = "언제 받을지 설정해야 합니다.")
    private int buttonDate;

    @NotBlank(message = "아티클의 주소가 필요합니다.")
    private String imgOg;

    @NotBlank(message = "아티클의 ID가 필요합니다")
    private long articleId;
}
