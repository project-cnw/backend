package com.example.project_cnw.dto.request.teacher;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateNoticeRequestDto {
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    @NotBlank(message = "대상을 선택해주세요.")
    @Pattern(regexp = "ALL|STUDENT", message = "올바른 대상을 선택해주세요.")
    private String targetAudience;

    private java.time.LocalDate startDate;
    private java.time.LocalDate endDate;
}
