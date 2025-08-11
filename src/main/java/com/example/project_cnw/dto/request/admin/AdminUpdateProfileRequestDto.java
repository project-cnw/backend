package com.example.project_cnw.dto.request.admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminUpdateProfileRequestDto {
    @NotBlank(message = "이름을 입력해주세요.")
    @Size(min = 2, max = 20, message = "이름은 2-20자 사이어야 합니다.")
    private String name;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "전화번호를 입력해주세요.")
    @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "전화번호는 010-0000-0000 형식이어야 합니다.")
    private String phoneNumber;
}
