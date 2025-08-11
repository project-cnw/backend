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
public class MyCourseResponseDto {
    private List<MyCourse> myCourses;
    private int totalCredits;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MyCourse {
        private Long registrationId;
        private String subjectName;
        private String teacherName;
        private Integer credits;
        private String dayOfWeek;
        private String classPeriod;
        private String status;
        private String approvalStatus;
    }
}
