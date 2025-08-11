package com.example.project_cnw.dto.request.teacher;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfileRequestDto {
    private String phoneNumber;
    private String email;
    private String subject;
}
