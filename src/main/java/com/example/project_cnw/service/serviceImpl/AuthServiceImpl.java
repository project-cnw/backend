package com.example.project_cnw.service.serviceImpl;

import com.example.project_cnw.common.enums.StudentAffiliation;
import com.example.project_cnw.common.enums.StudentStatus;
import com.example.project_cnw.common.enums.TeacherStatus;
import com.example.project_cnw.dto.request.auth.*;
import com.example.project_cnw.dto.response.auth.LoginResponseDto;
import com.example.project_cnw.dto.response.auth.RefreshTokenResponseDto;
import com.example.project_cnw.entity.*;
import com.example.project_cnw.exception.DataNotFoundException;
import com.example.project_cnw.exception.DuplicateDataException;
import com.example.project_cnw.exception.EmailVerificationException;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

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

        switch (role) {
            case "STUDENT":
                Student student = studentRepository.findByStudentUsername(username)
                        .orElseThrow(() -> new AuthenticationException("존재하지 않는 학생 계정입니다."));

                if (student.getStudentStatus() != StudentStatus.APPROVED) {
                    throw new AuthenticationException("승인되지 않은 계정입니다. 관리자의 승인을 기다려주세요.");
                }

                userId = student.getStudentId();
                encodePassword = student.getStudentPassword();
                userRole = "STUDENT";
                break;

            case "TEACHER":
                Teacher teacher = teacherRepository.findByTeacherUsername(username)
                        .orElseThrow(() -> new AuthenticationException("존재하지 않는 교사 계정입니다."));

                if (teacher.getTeacherStatus() != TeacherStatus.APPROVED) {
                    throw new AuthenticationException("승인되지 않은 계정입니다. 관리자의 승인을 기다려주세요.");
                }

                userId = teacher.getTeacherId();
                encodePassword = teacher.getTeacherPassword();
                userRole = "TEACHER";
                break;

            case "ADMIN":
                School school = schoolRepository.findBySchoolAdminUsername(username)
                        .orElseThrow(() -> new AuthenticationException("존재하지 않는 관리자 계정입니다."));

                if (!passwordEncoder.matches(password, school.getSchoolAdminPassword())) {
                    throw new AuthenticationException("비밀번호가 일치하지 않습니다.");
                }

                Optional<Admin> adminOpt = adminRepository.findBySchoolId(school.getSchoolId());

                if(adminOpt.isEmpty()) {
                    String accessToken = jwtProvider.generateAccessToken(username, "ADMIN", school.getSchoolId());
                    String refreshToken = jwtProvider.generateRefreshToken(username);

                    return LoginResponseDto.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshToken)
                            .build();
                }

                userId = adminOpt.get().getAdminId();
                encodePassword = school.getSchoolAdminPassword();
                userRole = "ADMIN";
                break;

            default:
                throw new InvalidRequestExceprion("올바른 역할을 선택해주세요.");
            }

        if (!role.equals("ADMIN") && !passwordEncoder.matches(password, encodePassword)) {
            throw new AuthenticationException("비밀번호가 일치하지 않습니다.");
        }

        String accessToken = jwtProvider.generateAccessToken(username, userRole, userId);
        String refreshToken = jwtProvider.generateRefreshToken(username);

        return LoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public RefreshTokenResponseDto refreshToken(RefreshTokenRequestDto request) {
        String refreshToken = request.getRefreshToken();

        if (!jwtProvider.validateToken(refreshToken || !jwtProvider.isRefreshToken)) {
            throw new AuthenticationException("유효하지 않은 리프레시 토큰입니다.");
        }

        String username = jwtProvider.getUsernameFromToken(refreshToken);

        Optional<Student> student = studentRepository.findByStudentUsername(username);
        if (student.isPresent()) {
            String newAccessToken = jwtProvider.generateAccessToken(username, "STUDENT", student.get().getStudentId());
            String newRefreshToken = jwtProvider.generateRefreshToken(username);

            return RefreshTokenResponseDto.builder()
                    .accessToken(newAccessToken)
                    .refreshToken(newRefreshToken)
                    .build();
        }

        Optional<Teacher> teacher = teacherRepository.findByTeacherUsername(username);
        if (teacher.isPresent()) {
            String newAccessToken = jwtProvider.generateAccessToken(username, "TEACHER", teacher.get().getTeacherId());
            String newRefreshToken = jwtProvider.generateRefreshToken(username);

            return RefreshTokenResponseDto.builder()
                    .accessToken(newAccessToken)
                    .refreshToken(newRefreshToken)
                    .build();
        }

        Optional<School> school = schoolRepository.findBySchoolAdminUsername(username);
        if (school.isPresent()) {
            Optional<Admin> admin = adminRepository.findBySchoolId(school.get().getSchoolId());
            Long userId = admin.map(Admin::getAdminId).orElse(school.get().getSchoolId());

            String newAccessToken = jwtProvider.generateAccessToken(username, "ADMIN", userId);
            String newRefreshToken = jwtProvider.generateRefreshToken(username);

            return RefreshTokenResponseDto.builder()
                    .accessToken(newAccessToken)
                    .refreshToken(newRefreshToken)
                    .build();
        }

        throw new AuthenticationException("사용자를 찾을 수 없습니다.");
    }

    @Override
    public void logout(LogoutRequestDto request) {
        log.info("사용자 로그아웃");
    }

    @Override
    public void sendVerificationEmail(SendVerificationRequestDto request) {

        emailVerificationRepository.deleteByEmailVerificationEmail(email);

        String verificationCode = emailProvider.generateVerificationCode();
        String token = UUID.randomUUID().toString();

        EmailVerification verification = new EmailVerification();
        verification.setEmailVerificationEmail(email);
        verification.setEmailVerificationCode(verificationCode);
        verification.setEmailVerificationToken(token);
        verification.setEmailVerificationExpiresAt(LocalDateTime.now().plusMinutes(10));
        verification.setEmailVerificationIsVerified(false);

        emailVerificationRepository.save(verification);

        boolean sent = emailProvider.sendVerificationEmail(email, verificationCode);
        if(!sent) {
            throw new EmailVerificationException("이메일 발송에 실패했습니다.");
        }
    }

    @Override
    public void verifyEmail(VerifyEmailRequestDto request) {
        String email = request.getEmail();
        String code = request.getVerificationCode();

        EmailVerification verification = emailVerificationRepository
                .findByEmailVerificationEmailAndEmailVerificationCode(email, code)
                .oreElseThrow(() -> new EmailVerificationException("잘못된 인증번호입니다."));

        if (verification.getEmailVerificationExpiresAt().isBefore(LocalDateTime.now())) {
            throw new EmailVerificationException("인증번호가 만료되었습니다.");
        }

        verification.setEmailVerificationIsVerified(true);
        emailVerificationRepository.save(verification);
    }

    @Override
    public void studentSignup(StudentSignupRequestDto request) {
        EmailVerification verification = emailVerificationRepository
                .findByEmailVerificationEmailAndEmailVerificationCode(request.getEmail(), request.getVerificationCode())
                .orElseThrow(() -> new EmailVerificationException("이메일 인증이 완료되지 않았습니다."));

        if (!verification.getEmailVerificationIsVerified()) {
            throw new EmailVerificationException("이메일 인증이 완료되지 않았습니다.");
        }

        if (studentRepository.existsByStudentUsername(request.getUsername())) {
            throw new DuplicateDataException("이미 사용중인 아이디입니다.");
        }

        if (studentRepository.existsByStudentEmail(request.getEmail())) {
            throw new DuplicateDataException("이미 사용중인 이메일입니다.");
        }

        if (studentRepository.existsByStudentNumber(request.getStudentNumber())) {
            throw new DuplicateDataException("이미 사용 중인 학번입니다.");
        }

        School school = schoolRepository.findBySchoolName(request.getSchoolName())
                .orElseThrow(() -> new DataNotFoundException("등록되지 않은 학교입니다."));
    }

    LocalDate birthDate = LocalDate.parse(request.getBirthDate(), DateTimeFormatter.ofPattern("yyyyMMdd"));

    Student student = new Student();
    student.setSchoolId(school.getSchoolId());
    student.setStudentUsername(request.getUsername());
    student.setStudentPassword(psswordEncoder.encode(request.getPassword()));
    student.setStudentName(request.getName());
    student.setStudentEmail(request.getEmail());
    student.setStudentPhoneNumber(request.getPhoneNumber());
    student.setStudentBirthDate(birthDate);
    student.setStudentNumber(request.getStudentNumber());
    student.setStudentGrade(request.getGrade());
    student.setStudentAffiliation(StudentAffiliation.valueOf(request.getAffiliation()));
    student.setStudentAdmissionYear(request.getAdmissionYear());
    student.setStudentStatus(StudentStatus.PENDING);

    studentRepository.save(student);

    emailVerificationRepository.delete(verification);
    }

    @Override
    public void teacherSignup(TeacherSignupRequestDto request) {
        EmailVerification verification = emailVerificationRepository
                .findByEmailVerificationEmailAndEmailVerificationCode(request.getEmail(), request.getVerificationCode())
                .orElseThrow(() -> new EmailVerificationException("이메일 인증이 완료되지 않았습니다."));

        if (! verification.getEmailVerificationIsVerified()) {
            throw new EmailVerificationException("이메일 인증이 완료되지 않았습니다.");
        }
    }



