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
public class InquiryListResponseDto {
    private List<InquiryInfo> inquiries;
    private int totalPages;
    private long totalElements;
    private int currentPage;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InquiryInfo {
        private Long inquiryId;
        private String title;
        private String content;
        private String authorType;
        private String status;
        private LocalDate createdAt;
    }
}
