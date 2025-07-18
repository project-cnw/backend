package com.example.project_cnw.entity;

import com.example.project_cnw.common.enums.TeacherStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "teacher")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Teacher {
    @Id
    @Column(name = "teacher_id")
    private Long teacherId;

    @Column(name = "school_id", nullable = false)
    private Long schoolId;

    @Column(name = "teacher_username", unique = true, nullable = false)
    private String teacherUsername;

    @Column(name = "teacher_password", nullable = false)
    private String teacherPassword;

    @Column(name = "teacher_name", nullable = false)
    private String teacherName;

    @Column(name = "teacher_email", unique = true, nullable = false)
    private String teacherEmail;

    @Column(name = "teacher_phone_number", nullable = false)
    private String teacherPhoneNumber;

    @Column(name = "teacher_subject", nullable = false)
    private String teacherSubject;

    @Column(name = "teacher_birth_date", nullable = false)
    private LocalDate teacherBirthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "teacher_status")
    private TeacherStatus teacherStatus = TeacherStatus.PENDING;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
