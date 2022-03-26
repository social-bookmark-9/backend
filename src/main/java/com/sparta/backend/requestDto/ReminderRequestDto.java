package com.sparta.backend.requestDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReminderRequestDto {

    @NotBlank(message = "아티클의 제목이 있어야 합니다.")
    private String titleOg;

    @NotNull(message = "buttonDate가 없습니다.")
    private Integer buttonDate;

    @NotBlank(message = "아티클의 주소가 필요합니다.")
    private String imgOg;

    @NotNull(message = "아티클의 ID가 필요합니다")
    private Long articleId;
}
