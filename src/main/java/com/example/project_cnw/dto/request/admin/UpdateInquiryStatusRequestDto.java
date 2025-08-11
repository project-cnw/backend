package com.example.project_cnw.dto.request.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateInquiryStatusRequestDto {
    @NotBlank(message = "상태를 선택해주세요.")
    @Pattern(regexp = "NEW|IN_PROGRESS|CLOSED", message = "올바른 상태를 선택해주세요.")
    private String status;
}
