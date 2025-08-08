package com.example.project_cnw.service.auth;

import com.example.project_cnw.dto.request.auth.LoginRequestDto;
import com.example.project_cnw.dto.response.auth.LoginResponseDto;

public interface AuthService {
    LoginResponseDto login(LoginRequestDto request);

}
