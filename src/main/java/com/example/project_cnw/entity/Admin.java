package com.example.project_cnw.entity;

import com.example.project_cnw.entity.datetime.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "admin")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Admin extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long adminId;

    @Column(name = "school_id", nullable = false)
    private Long schoolId;

    @Column(name = "admin_name", nullable = false)
    private String adminName;

    @Column(name = "admin_username", unique = true, nullable = false)
    private String adminUsername;

    @Column(name = "admin_password", nullable = false)
    private String adminPassword;

    @Column(name = "admin_email", nullable = false)
    private String adminEmail;

    @Column(name = "admin_birth_date", nullable = false)
    private LocalDate adminBirthDate;

    @Column(name = "admin_phone_number", nullable = false)
    private String adminPhoneNumber;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
