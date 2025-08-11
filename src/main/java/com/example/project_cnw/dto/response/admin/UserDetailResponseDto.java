package com.example.project_cnw.dto.response.admin;

import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailResponseDto {
    private Long userId;
    private String name;
    private String username;
    private String email;
    private String phoneNumber;
    private LocalDate birthDate;
    private String role;
    private String status;
    private String schoolName;

    private String studentNumber;
    private String grade;
    private String affiliation;
    private Integer admissionYear;

    private String subject;
}
