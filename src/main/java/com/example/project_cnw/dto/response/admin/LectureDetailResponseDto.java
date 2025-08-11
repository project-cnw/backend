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
public class LectureDetailResponseDto {
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
    private List<StudentInfo> enrolledStudents;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StudentInfo {
        private Long studentId;
        private String studentName;
        private String studentNumber;
        private String grade;
        private String registrationStatus;
    }
}
