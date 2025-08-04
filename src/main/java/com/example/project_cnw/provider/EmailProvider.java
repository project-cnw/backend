package com.example.project_cnw.provider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailProvider {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    private static final SecureRandom random = new SecureRandom();

    public String generateVerificationCode() {
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    public boolean sendVerificationEmail(String toEmail, String verificationCode) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("[고교학점제] 이메일 인증번호");
            message.setText(buildVerificationEmailContent(verificationCode));

            mailSender.send(message);
            log.info("Verification email sent to: {}", toEmail);
            return true;
        } catch (Exception e) {
            log.error("Failed to send verification email to: {}", toEmail, e);
            return false;
        }
    }

    public boolean sendTemporaryPassword(String toEmail, String temporaryPassword) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("[고교학점제] 임시 비밀번호");
            message.setText(buildTemporaryPasswordEmailContent(temporaryPassword));

            mailSender.send(message);
            log.info("Temporary password email sent to: {}", toEmail);
            return true;
        } catch (Exception e) {
            log.error("Failed to send temporary password email to: {}", toEmail, e);
            return false;
        }
    }

    public boolean sendFoundUsername(String toEmail, String username) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("[고교학점제] 아이디 찾기 결과");
            message.setText(buildFoundUsernameEmailContent(username));

            mailSender.send(message);
            log.info("Found username email sent to: {}", toEmail);
            return true;
        } catch(Exception e) {
            log.error("Failed to send found username email to: {}", toEmail, e);
            return false;
        }
    }

    public boolean sendApprovalNotification(String toEmail, String name, String role) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(toEmail);
            message.setSubject("[고교학점제] 회원가입 승인 완료");
            message.setText(buildApprovalNotificationContent(name, role));

            mailSender.send(message);
            log.info("Approval notification email sent to: {}", toEmail);
            return true;
        } catch(Exception e) {
            log.error("Failed to send approval notification email to: {}", toEmail, e);
            return false;
        }
    }

    public String generateTemporaryPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();

        for(int i=0; i<8; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }

        return password.toString();
    }

    private String buildVerificationEmailContent(String verificationCode) {
        return String.format(
                "안녕하세요. 고교학점제 수강신청 시스템입니다.\n\n" +
                "이메일 인증을 위한 인증번호를 알려드립니다.\n\n" +
                "인증번호: %s\n\n" +
                "인증번호는 10분간 유효합니다.\n" +
                "감사합니다.",
                verificationCode
        );
    }

    private String buildTemporaryPasswordEmailContent(String temporaryPassword) {
        return String.format(
                "안녕하세요. 고교학점제 수강신청 시스템입니다.\n\n" +
                "임시 비밀번호를 알려드립니다.\n\n" +
                "임시 비밀번호: %s\n\n" +
                "로그인 후 반드시 비밀번호를 변경해주세요.\n" +
                "갑사합니다.",
                temporaryPassword
        );
    }

    private String buildFoundUsernameEmailContent(String username) {
        return String.format(
                "안녕하세요. 고교학점제 수강신청 시스템입니다.\n\n" +
                "요청하신 아이디 찾기 결과를 알려드립니다.\n\n" +
                "아이디: %s\n\n" +
                "감사합니다.",
                username
        );
    }

    private String buildApprovalNotificationContent(String name, String role) {
        String roleKorean = role.equals("STUDENT") ? "학생" : "교사";
        return String.format(
                "안녕하세요. %s님\n\n" +
                "고교학점제 수강신청 시스템 %s 회원가입이 승인되었습니다.\n\n" +
                "이제 시스템에 로그인하여 서비스를 이용하실 수 있습니다.\n\n" +
                "감사합니다.",
                name, roleKorean
        );
    }
}
