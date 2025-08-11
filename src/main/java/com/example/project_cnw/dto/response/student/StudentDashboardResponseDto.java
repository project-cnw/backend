package com.example.project_cnw.dto.response.student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDashboardResponseDto {
    private long availableCredits;
    private long appliedCredits;
    private long totalLectures;
    private long newNotices;
}
