package com.example.project_cnw.dto.request.student;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BulkCourseRegistrationRequestDto {
    @NotNull(message = "강의 ID 목록을 입력해주세요.")
    private List<Long> lectureIds;
}
