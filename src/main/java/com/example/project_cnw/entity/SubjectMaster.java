package com.example.project_cnw.entity;

import com.example.project_cnw.common.enums.SubjectAffiliation;
import com.example.project_cnw.common.enums.SubjectDayOfWeek;
import com.example.project_cnw.common.enums.SubjectType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "subject_master")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_master_id")
    private Long subjectMasterId;

    @Column(name = "subject_code", unique = true, nullable = false)
    private String subjectCode;

    @Column(name = "subject_name", nullable = false)
    private String subjectName;

    @Enumerated(EnumType.STRING)
    @Column(name = "subject_type", nullable = false)
    private SubjectType subjectType;

    @Enumerated(EnumType.STRING)
    @Column(name = "subject_affiliation", nullable = false)
    private SubjectAffiliation subjectAffiliation;

    @Column(name = "subject_available_grades", nullable = false)
    private String subjectAvailableGrades;

    @Column(name = "subject_credits", nullable = false, precision = 2, scale = 1)
    private BigDecimal subjectCredits = new BigDecimal("3.0");

    @Column(name = "subject_description", nullable = false, columnDefinition = "TEXT")
    private String subjectDescription;

    @Column(name = "subject_classroom", nullable = false)
    private String StringClassroom;

    @Enumerated(EnumType.STRING)
    @Column(name = "subject_day_of_week", nullable = false)
    private SubjectDayOfWeek subjectDayOfWeek;

    @Column(name = "subject_class_period", nullable = false)
    private String subjectClassPeriod;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updateAt;

}
