package com.example.project_cnw.dto.response.teacher;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubjectMasterListResponseDto {
    private List<SubjectMasterInfo> subjectMasters;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SubjectMasterInfo {
        private Long subjectMasterId;
        private String subjectCode;
        private String subjectName;
        private String subjectType;
        private String subjectAffiliation;
        private String availableGrades;
        private String description;
        private String dayOfWeek;
        private String classPeriod;
        private String classroom;
        private Integer credits;
    }
}
