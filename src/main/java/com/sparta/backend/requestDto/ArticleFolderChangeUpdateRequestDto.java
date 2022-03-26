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
public class ArticleFolderChangeUpdateRequestDto {
    @NotBlank(message = "아티클 폴더를 반드시 선택해야 합니다.")
    private String articleFolderName;
}
