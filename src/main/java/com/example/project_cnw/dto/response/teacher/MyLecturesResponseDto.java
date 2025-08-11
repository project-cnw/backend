package com.example.project_cnw.dto.response.teacher;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyLecturesResponseDto {
    private List<MyLecture> myLectures;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyLecture {
        private Long lectureId;
        private String lectureCode;
        private String subjectName;
        private String allowedGrade;
        private String dayOfWeek;
        private String classPeriod;
        private Integer maxEnrollment;
        private Integer currentEnrollment;
        private String status;
    }
}
