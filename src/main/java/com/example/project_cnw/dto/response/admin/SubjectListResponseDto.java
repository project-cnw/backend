package com.example.project_cnw.dto.response.admin;

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
public class SubjectListResponseDto {
    private List<SubjectInfo> subjects;
    private int totalPages;
    private long totalElements;
    private int currentPage;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SubjectInfo {
        private Long subjectId;
        private String subjectCode;
        private String subjectName;
        private String teacherName;
        private String targetGrade;
        private String semester;
        private String status;
        private Integer maxEnrollment;
        private LocalDate createdAt;
    }
}
