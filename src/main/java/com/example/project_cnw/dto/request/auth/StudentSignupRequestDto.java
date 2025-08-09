package com.example.project_cnw.dto.request.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.parameters.P;

@Getter
@Setter
public class StudentSignupRequestDto {
    @NotBlank(message = "이름을 입력해주세요.")
    @Size(min = 2, max = 20, message = "이름은 2-20자 사이여야 합니다.")
    private String name;

    @NotBlank(message = "아이디를 입력해주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9_]{4,20}$", message = "아이디는 4-20자의 영문, 숫자, 언더바만 가능합니다." )
    private String username;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d@$!%*?&]{8,}$", message = "비밀번호는 8자 이상, 영문과 숫자를 포함해야 합니다.")
    private String password;

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "전화번호를 입력해주세요.")
    @Pattern(regexp = "^010-\\d-\\d{4}$", message = "전화번호는 010-0000-0000 형식이어야 합니다.")
    private String phoneNumber;

    @NotBlank(message = "생년월일을 입력해주세요.")
    @Pattern(regexp = "^\\d{8}$", message = "생년월일은 YYYYMMDD 형식이어야 합니다.")
    private String birthDate;

    @NotBlank(message = "학교명을 입력해주세요.")
    private String schoolName;

    @NotBlank(message = "학번을 입력해주세요.")
    @Pattern(regexp = "^\\d{8}$", message = "학번은 8자리 숫자여야합니다.")
    private String studentNumber;

    @NotBlank(message = "학년을 선택해주세요.")
    @Pattern(regexp = "^[1-3]$", message = "학년은 1,2,3 중 선택해주세요.")
    private String grade;

    @NotBlank(message = "계열을 선택해주세요.")
    @Pattern(regexp = "LIBERAL_ARTS|NATURAL_SCIENCES", message = "올바른 계열을 선택해주세요.")
    private String affiliation;

    private Integer admissionYear;
}
