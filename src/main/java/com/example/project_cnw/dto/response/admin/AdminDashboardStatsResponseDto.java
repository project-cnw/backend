package com.example.project_cnw.dto.response.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminDashboardStatsResponseDto {
    private long totalStudents;
    private long totalTeachers;
    private long totalSubjects;
    private long totalLectures;
    private long pendingApprovals;
    private long totalNotices;
    private long newInquiries;
}
