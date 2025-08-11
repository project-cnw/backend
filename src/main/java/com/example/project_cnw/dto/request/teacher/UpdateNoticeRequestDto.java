package com.example.project_cnw.dto.request.teacher;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateNoticeRequestDto {
    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    @NotBlank(message = "대상을 선택해주세요.")
    private String targetAudience;

    private LocalDate startDate;
    private LocalDate endDate;
}
