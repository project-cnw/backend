package com.example.project_cnw.dto.response.teacher;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeacherProfileResponseDto {
    private String name;
    private String username;
    private String email;
    private String phoneNumber;
    private LocalDate birthDate;
    private String schoolName;
    private String subject;
}
