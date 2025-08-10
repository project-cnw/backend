package com.example.project_cnw.dto.response.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SchoolListResponseDto {
    private List<SchoolInfo> schools;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SchoolInfo {
        private Long schoolId;
        private String schoolName;
        private Integer schoolCode;
    }
}
