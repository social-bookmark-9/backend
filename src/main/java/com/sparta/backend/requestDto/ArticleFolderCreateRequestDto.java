package com.sparta.backend.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleFolderCreateRequestDto {

    @NotBlank(message = "컬랙션 이름 필요")
    private String articleFolderName;

    @NotNull(message = "공개여부를 누락")
    private Boolean folderHide;
}
