package com.example.project_cnw.dto.request.auth;

import jakarta.validation.constraints.Email;
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
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[a-zA-Z](?=.*\\d)[a-zA-Z\\d@$!%*?&]{8,}$", message = "비밀번호는 8자 이상, 영문과 숫자, 특수문자를 포함해야 합니다.")
    private String password;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "전화번호를 입력해주세요.")
    @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = "전화번호는 010-0000-0000 형식이어야 합니다.")
    private String phoneNumber;

    @NotBlank(message = "생년월일을 입력해주세요.")
    @Pattern(regexp = "^\\d{8}$", message = "생년월일은 YYYYMMDD 형식이어야 합니다.")
    private String birthDate;

    @NotBlank(message = "학교명을 입력해주세요.")
    private String schoolName;

    @NotBlank(message = "담당과목을 입력해주세요.")
    private String subject;

    @NotBlank(message = "인증번호를 입력해주세요.")
    @Pattern(regexp = "\\d{6}", message = "인증번호는 6자리 숫자입니다.")
    private String verificationCode;

}
