package com.example.project_cnw.dto.response.student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimetableResponseDto {
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
        private String subjectName;
        private String teacherName;
        private String allowedGrade;
        private Integer credits;
        private Integer currentEnrollment;
        private String dayOfWeek;
        private String classPeriod;
        private String classroom;
    }

}
