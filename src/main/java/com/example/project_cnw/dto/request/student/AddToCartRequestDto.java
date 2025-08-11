package com.example.project_cnw.dto.request.student;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddToCartRequestDto {
    @NotNull(message = "강의 ID를 입력해주세요.")
    private Long lectureId;
}
