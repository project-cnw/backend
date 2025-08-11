package com.example.project_cnw.controller;

import com.example.project_cnw.common.ApiMappingPattern;
import com.example.project_cnw.common.ResponseDto;
import com.example.project_cnw.dto.request.auth.*;
import com.example.project_cnw.dto.response.auth.FindUsernameResponseDto;
import com.example.project_cnw.dto.response.auth.RefreshTokenResponseDto;
import com.example.project_cnw.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/auth/refresh")
    @Operation(summary = "토큰 갱신", description = "Refresh Token을 사용하여 새로운 Access Token을 발급합닌다.")
    public ResponseEntity<ResponseDto> refreshToken(@Valid @RequestBody RefreshTokenRequestDto request) {
        RefreshTokenResponseDto response = authService.refreshToken(request);
        return ResponseEntity.ok(ResponseDto.success("토큰이 갱신되었습니다.", response));
    }

    @PostMapping("/auth/logout")
    @Operation(summary = "로그아웃", description = "사용자 로그아웃을 처리합니다.")
    public ResponseEntity<ResponseDto> logout(@Valid @RequestBody LogoutRequestDto request) {
        authService.logout(request);
        return ResponseEntity.ok(ResponseDto.success("로그아웃되었습니다."));
    }

    @PostMapping("/auth/send-verification")
    @Operation(summary = "이메일 인증번호 발송", description = "이메일로 인증번호를 발송합니다.")
    public ResponseEntity<ResponseDto> sendVerificationEmail(@Valid @RequestBody SendVerificationRequestDto request) {
        authService.sendVerificationEmail(request);
        return ResponseEntity.ok(ResponseDto.success("인증번호가 이메일로 발송되었습니다."));
    }

    @PostMapping("/auth/verify-email")
    @Operation(summary = "이메일 인증", description = "이메일 인증번호를 확인합니다.")
    public ResponseEntity<ResponseDto> verifyEmail(@Valid @RequestBody VerifyEmailRequestDto request) {
        authService.verifyEmail(request);
        return ResponseEntity.ok(ResponseDto.success("이메일 인증이 완료되었습니다."));
    }

    @PostMapping("/auth/signup/student")
    @Operation(summary = "학생 회원가입", description = "학생 회원가입을 처리합니다.")
    public ResponseEntity<ResponseDto> studentSignup(@Valid @RequestBody studentSignupRequestDto request) {
        authService.studentSignup(request);
        return ResponseEntity.ok(ResponseDto.success("회원가입이 완료되었습니다. 관리자 승인을 기다려주세요."));
    }

    @PostMapping("/auth/signup/teacher")
    @Operation(summary = "교사 회원가입", description = "교사 회원가입을 처리합니다.")
    public ResponseEntity<ResponseDto> teacherSignup(@Valid @RequestBody TeacherSignupRequestDto request) {
        authService.teachersignup(request);
        return ResponseEntity.ok(ResponseDto.success("회원가입이 완료되었습니다. 관리자의 승인을 기다려주세요."));
    }

    @PostMapping("/auth/find-username")
    @Operation(summary = "아이디 찾기", description = "이메일 인증을 통해 아이디를 찾습니다.")
    public ResponseEntity<ResponseDto> findPassword(@Valid @RequestBody FindPasswordRequestDto request) {
        FindUsernameResponseDto response = authService.findUsername(request);
        return ResponseEntity.ok(ResponseDto.success("아이디를 찾았습니다.", response));
    }

    @PostMapping("/auth/find-password")
    @Operation(summary = "비밀번호 찾기", description = "아이디와 이메일 인증을 통해 임시 비밀번호를 발송합니다.")
    public ResponseEntity<ResponseDto> findPassword(@Valid @RequestBody FindPasswordRequestDto request) {
        FindUsernameResponseDto response = authService.findUsername(request);
        return ResponseEntity.ok(ResponseDto.success("임시 비밀번호가 이메일로 발송되었습니다."));
    }

    @PostMapping("/auth/change-password")
    @Operation(summary = "비밀번호 찾기", description = "아이디와 이메일 인증을 통해 임시 비밀번호를 발송합니다.")
    public ResponseEntity<ResponseDto> changePassword(@Valid @RequestBody FindPasswordRequestDto request) {
        authService.changePassword(request);
        return ResponseEntity.ok(ResponseDto.success("비밀번호가 변경되었습니다."));
    }

    @GetMapping("/schools")
    @Operation(summary = "학교 목록 조회", description = "등록된 학교 목록을 조회합니다.")
    public ResponseEntity<ResponseDto> getSchools() {
        var response = authService.getSchools();
        return ResponseEntity.ok(ResponseDto.success("학교 목록을 조회했습니다.", response));
    }
}
