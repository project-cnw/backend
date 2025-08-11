package com.example.project_cnw.dto.response.student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentProfileResponseDto {
    private String name;
    private String username;
    private String email;
    private String phoneNumber;
    private LocalDate birthDate;
    private String schoolName;
    private String studentNumber;
    private String grade;
    private String affiliation;
    private Integer admissionYear;
    private BigDecimal totalCredits;
}
