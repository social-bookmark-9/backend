package com.sparta.backend.requestDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
public class MemberNameDuplicateDto {
    @NotBlank(message = "유저명을 입력해 주세요")
    private String memberName;
}
