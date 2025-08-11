package com.example.project_cnw.dto.response.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LectureListResponseDto {
    private List<LectureInfo> lectures;
    private int totalPages;
    private long totalElements;
    private int currentPage;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LectureInfo {
        private Long lectureId;
        private String lectureCode;
        private String lectureName;
        private String subjectName;
        private String teacherName;
        private String allowedGrade;
        private Integer academicYear;
        private String semester;
        private Integer maxEnrollment;
        private Integer currentEnrollment;
    }
}
