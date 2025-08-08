package com.example.project_cnw.controller;

import com.example.project_cnw.common.ApiMappingPattern;
import com.example.project_cnw.common.ResponseDto;
import com.example.project_cnw.dto.request.auth.LoginRequestDto;
import com.example.project_cnw.dto.response.auth.LoginResponseDto;
import com.example.project_cnw.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiMappingPattern.API_COMMON)
@RequiredArgsConstructor
@Tag(name = "인증", description = "로그인, 회원가입, 이메일 인증 등 인증 관련 API")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/login")
    @Operation(summary = "로그인", description = "사용자 로그인을 처리합니다.")
    public ResponseEntity<ResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
        LoginResponseDto response = authService.login(request);
        return ResponseEntity.ok(ResponseDto.success("로그인에 성공했습니다.", response));
    }
}
