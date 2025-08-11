package com.example.project_cnw.dto.response.student;

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
    private int currentPages;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NoticeInfo {
        private Long noticeId;
        private String title;
        private String authorName;
        private LocalDate createdAt;
        private Integer viewCount;
    }
}
