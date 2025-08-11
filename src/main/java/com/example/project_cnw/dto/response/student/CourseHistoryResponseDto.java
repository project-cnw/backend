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
public class CourseHistoryResponseDto {
    private List<HistoryItem> history;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HistoryItem {
        private Integer academicYear;
        private String semester;
        private String subjectName;
        private String teacherName;
        private Integer credits;
        private String grade;
    }
}
