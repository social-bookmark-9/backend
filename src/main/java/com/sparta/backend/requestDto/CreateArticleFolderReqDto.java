package com.sparta.backend.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateArticleFolderReqDto {

    @NotBlank(message = "컬랙션 이름 필요")
    private String articleFolderName;

}
