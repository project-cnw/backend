package com.example.project_cnw.service;

import com.example.project_cnw.dto.request.auth.*;
import com.example.project_cnw.dto.response.auth.*;
import com.example.project_cnw.dto.response.common.SchoolListResponseDto;


public interface AuthService {
    LoginResponseDto login(LoginRequestDto request);
    RefreshTokenRequestDto refreshToken(RefreshTokenRequestDto request);
    void logout(LogoutRequestDto request);
    void sendVerificationEmail(SendVerificationRequestDto request);
    void verifyEmail(VerifyEmailRequestDto request);
    void studentSignup(StudentSignupRequestDto request);
    void teacherSignup(TeacherSignupRequestDto request);
    FindUsernameResponseDto findUsername(FindUsernameRequestDto request);
    void findPassword(FindPasswordRequestDto request);
    void changePassword(ChangePasswordRequestDto request);
    SchoolListResponseDto getSchools();

}
