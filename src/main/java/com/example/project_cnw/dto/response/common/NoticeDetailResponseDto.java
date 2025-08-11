package com.example.project_cnw.dto.response.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDetailResponseDto {
    private Long noticeId;
    private String title;
    private String content;
    private String authorName;
    private String authorType;
    private String targetAudience;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer viewCount;
    private LocalDate createdAt;
}
