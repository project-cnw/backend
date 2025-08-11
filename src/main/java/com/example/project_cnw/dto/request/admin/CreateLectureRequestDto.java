package com.example.project_cnw.dto.request.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateLectureRequestDto {
    @NotBlank(message = "과목 ID를 입력해주세요.")
    private Long subjectId;

    @NotBlank(message = "강의명을 입력해주세요.")
    private String lectureName;

    @NotBlank(message = "강의 코드를 입력해주세요.")
    private String lectureCode;

    @NotNull(message = "입학 연도를 입력해주세요")
    private Integer academicYear;

    @NotBlank(message = "학기를 선택해주세요.")
    @Pattern(regexp = "FIRST|SECOND", message = "올바른 학기를 선택해주세요.")
    private String semester;

    @NotBlank(message = "대상 학년을 입력해주세요.")
    private String allowedGrade;

    @NotNull(message = "최대 수강 인원을 입력해주세요.")
    private Integer maxEnrollment;
}
