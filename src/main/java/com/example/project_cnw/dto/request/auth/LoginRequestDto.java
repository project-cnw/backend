package com.example.project_cnw.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
    @NotBlank(message = "아이디를 입력해주세요.")
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message = "역할을 선택해주세요.")
    @Pattern(regexp = "STUDENT|TEACHER|ADMIN", message = "올바른 역할을 선택헤주세요.")
    private String role;
}
