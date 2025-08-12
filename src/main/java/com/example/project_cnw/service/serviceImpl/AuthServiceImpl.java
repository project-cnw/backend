package com.example.project_cnw.service.serviceImpl;

import com.example.project_cnw.common.enums.StudentStatus;
import com.example.project_cnw.dto.request.auth.LoginRequestDto;
import com.example.project_cnw.dto.response.auth.LoginResponseDto;
import com.example.project_cnw.entity.Student;
import com.example.project_cnw.provider.EmailProvider;
import com.example.project_cnw.provider.JwtProvider;
import com.example.project_cnw.repository.*;
import com.example.project_cnw.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AuthServiceImpl implements AuthService {
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final AdminRepository adminRepository;
    private final SchoolRepository schoolRepository;
    private final EmailVerificationRepository emailVerificationRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final EmailProvider emailProvider;

    @Override
    public LoginResponseDto login(LoginRequestDto request) {
        String role = request.getRole();
        String username = request.getUsername();
        String password = request.getPassword();

        Long userId;
        String encodePassword;
        String userRole;

        switch(role) {
            case "STUDENT":
                Student student = studentRepository.findByStudentUsername(username)
                        .orElseThrow(() -> new AuthenticationException("존재하지 않는 학생 계정입니다."));

                if(student.getStudentStatus() != StudentStatus.APPROVED) {
                    throw new AuthenticationException("승인되지 않은 계정입니다. 관리자의 승인을 기다려주세요.");
                }

                userId = student.getStudentId();
                encodePassword = student.getStudentPassword();
                userRole = "STUDENT";
                break;
        }
    }

}
