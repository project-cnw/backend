package com.example.project_cnw.entity;

import com.example.project_cnw.common.enums.CourseRegistrationAcademicStatus;
import com.example.project_cnw.common.enums.CourseRegistrationSemester;
import com.example.project_cnw.common.enums.CourseRegistrationStatus;
import com.example.project_cnw.common.enums.CourseRegistrationSubjectApprovalStatus;
import com.example.project_cnw.entity.datetime.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "course_registration")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseRegistration extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_registration_id")
    private Long courseRegistrationId;

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Column(name = "lecture_id", nullable = false)
    private Long lectureId;

    @Column(name = "course_registration_academic_year", nullable = false)
    private Integer courseRegistrationAcademicYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "course_registration_status")
    private CourseRegistrationStatus courseRegistrationStatus = CourseRegistrationStatus.CART;

    @Enumerated(EnumType.STRING)
    @Column(name = "course_registration_semester", nullable = false)
    private CourseRegistrationSemester courseRegistrationSemester;

    @Enumerated(EnumType.STRING)
    @Column(name = "course_registration_subject_approval_status")
    private CourseRegistrationSubjectApprovalStatus courseRegistrationSubjectApprovalStatus = CourseRegistrationSubjectApprovalStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(name = "course_registration_academic_status", nullable = false)
    private CourseRegistrationAcademicStatus courseRegistrationAcademicStatus;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
