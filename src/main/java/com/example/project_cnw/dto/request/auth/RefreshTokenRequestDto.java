package com.example.project_cnw.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshTokenRequestDto {
    @NotBlank(message = "리프레시 토큰을 입력해주세요.")
    private String refreshToken;
}
