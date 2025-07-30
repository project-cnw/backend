package com.example.project_cnw.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "school")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_id")
    private Long schoolId;

    @Column(name = "school_name", nullable = false)
    private String schoolName;

    @Column(name = "school_address", nullable = false)
    private String schoolAddress;

    @Column(name = "school_contact_number", nullable = false)
    private String schoolContactNumber;

    @Column(name = "school_code", unique = true, nullable = false)
    private Integer schoolCode;

    @Column(name = "school_email", unique = true, nullable = false)
    private String schoolEmail;

    @Column(name = "school_admin_username", unique = true, nullable = false)
    private String schoolAdminUsername;

    @Column(name = "school_admin_password", nullable = false)
    private String schoolAdminPassword;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

