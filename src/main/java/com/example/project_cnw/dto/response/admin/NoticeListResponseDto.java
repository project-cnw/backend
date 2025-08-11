package com.example.project_cnw.dto.response.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeListResponseDto {
    private List<NoticeInfo> notices;
    private int totalPages;
    private long totalElements;
    private int currentPage;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NoticeInfo {
        private Long noticeId;
        private String title;
        private String authorName;
        private String targetAudience;
        private LocalDate startDate;
        private LocalDate endDate;
        private Integer viewCount;
        private LocalDate createdAt;
    }
}
