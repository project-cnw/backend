package com.example.project_cnw.entity;

import com.example.project_cnw.common.enums.SubjectAffiliation;
import com.example.project_cnw.common.enums.StudentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "student")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "school_id", nullable = false)
    private Long schoolId;

    @Column(name = "student_username", unique = true, nullable = false)
    private String studentUsername;

    @Column(name = "student_password", nullable = false)
    private String studentPassword;

    @Column(name = "student_number", unique = true, nullable = false)
    private String studentNumber;

    @Column(name = "student_name", nullable = false)
    private String studentName;

    @Column(name = "student_grade", nullable = false)
    private String studentGrade;

    @Column(name = "student_email", unique = true, nullable = false)
    private String studentEmail;

    @Column(name = "student_phone_number", nullable = false)
    private String studentPhoneNumber;

    @Column(name = "student_birth_date", nullable = false)
    private LocalDate studentBirthDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "student_affiliation", nullable = false)
    private SubjectAffiliation studentAffiliation;

    @Enumerated(EnumType.STRING)
    @Column(name = "student_status", nullable = false)
    private StudentStatus studentStatus = StudentStatus.PENDING;

    @Column(name = "student_admission_year", nullable = false)
    private Integer studentAdmissionYear;

    @Column(name = "student_total_credits", precision = 5, scale = 1)
    private BigDecimal studentTotalCredits = new BigDecimal("192.0");

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
