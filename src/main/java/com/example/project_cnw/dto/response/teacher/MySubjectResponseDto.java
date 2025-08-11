package com.example.project_cnw.dto.response.teacher;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MySubjectResponseDto {
    private List<MySubject> mySubjects;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MySubject {
        private Long subjectId;
        private String subjectCode;
        private String subjectName;
        private String targetGrade;
        private String semester;
        private String status;
        private Integer maxEnrollment;
        private LocalDate createdAt;
    }
}
