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
public class CourseRegistrationListResponseDto {
    private List<AvailableCourse> availableCourses;
    private List<CartItem> carItems;
    private int totalCreditsInCart;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AvailableCourse {
        private Long lectureId;
        private String subjectName;
        private String teacherName;
        private String grade;
        private Integer credits;
        private String capacity;
        private String status;
        private String dayOfWeek;
        private String classPeriod;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CartItem {
        private Long lectureId;
        private String subjectName;
        private String teacherName;
        private Integer credits;
        private String dayOfWeek;
        private String classPeriod;
    }
}
