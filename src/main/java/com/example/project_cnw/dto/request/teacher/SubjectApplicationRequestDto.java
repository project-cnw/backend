package com.example.project_cnw.dto.request.teacher;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectApplicationRequestDto {
    @NotNull(message = "과목 마스터 ID를 입력해주세요.")
    private Long subjectMasterId;

    @NotBlank(message = "대상 학년을 입력해주세요.")
    private String targetGrade;

    @NotBlank(message = "학기를 선택해주세요.")
    @Pattern(regexp = "FIRST|SECOND", message = "올바른 학기를 선택해주세요.")
    private String semester;

    @NotNull(message = "최대 수강 인원을 입력해주세요.")
    private Integer maxEnrollment;
}
