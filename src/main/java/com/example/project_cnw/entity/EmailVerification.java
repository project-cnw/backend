package com.example.project_cnw.entity;

import com.example.project_cnw.entity.datetime.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "email_verification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailVerification extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_verification_id")
    private Long emailVerificationId;

    @Column(name = "email_verification_email", nullable = false)
    private String emailVerificationEmail;

    @Column(name = "email_verification_token", nullable = false)
    private String emailVerificationToken;

    @Column(name = "email_verification_code", nullable = false)
    private String emailVerificationCode;

    @Column(name = "email_verification_expires_at", nullable = false)
    private LocalDateTime emailVerificationExpiresAt;

    @Column(name= "email_verification_is_verified")
    private Boolean emailVerificationIsVerified = false;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
