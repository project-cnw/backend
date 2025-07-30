package com.example.project_cnw.entity;

import com.example.project_cnw.common.enums.LectureSemester;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "lecture")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Lecture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id")
    private Long lectureId;

    @Column(name = "school_id", nullable = false)
    private Long schoolId;

    @Column(name = "subject_id", nullable = false)
    private Long subjectId;

    @Column(name = "teacher_id", nullable = false)
    private Long teacherId;

    @Column(name = "lecture_name", nullable = false)
    private String lectureName;

    @Column(name = "lecture_code", nullable = false)
    private String lectureCode;

    @Column(name = "lecture_academic_year", nullable = false)
    private Integer lectureAcademicYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "lecture_semester", nullable = false)
    private LectureSemester lectureSemester;

    @Column(name = "lecture_allowed_grade", nullable = false)
    private String lectureAllowedGrade;

    @Column(name = "lecture_max_enrollment", nullable = false)
    private Integer lectureMaxEnrollment;

    @Column(name = "lecture_current_enrollment")
    private Integer lectureCurrentEnrollment = 0;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
