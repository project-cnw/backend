package com.example.project_cnw.dto.response.teacher;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDashboardResponseDto {
    private long totalLectures;
    private long totalStudents;
    private long weeklyLectures;
    private long newNotifications;
}
