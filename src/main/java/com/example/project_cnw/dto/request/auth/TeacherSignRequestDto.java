package com.example.project_cnw.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherSignRequestDto {
    @NotBlank(message = "이름을 입력해주세요.")
    @Size(min = 2, max = 20, message = "이름은 2-20자 사이여야 합니다.")
    private String name;

    @NotBlank(message = "아이디를 입력해주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9_],{4,20}", message = "아이디는 4-20자의 영문, 숫자, 언더바만 가능합니다.")

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern()
}
