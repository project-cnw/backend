package com.example.project_cnw.dto.response.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SchoolListResponseDto {
    private List<SchoolInfo> schools;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SchoolInfo {
        private Long schoolId;
        private String schoolName;
        private String schoolAddress;
        private String schoolContactNumber;
        private Integer schoolCode;
        private String schoolEmail;
    }
}
