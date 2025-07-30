package com.example.project_cnw.entity;

import com.example.project_cnw.common.enums.SubjectSemester;
import com.example.project_cnw.common.enums.SubjectStatus;
import com.example.project_cnw.common.enums.SubjectType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "subject")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Long subjectId;

    @Column(name = "school_id", nullable = false)
    private Long schoolId;

    @Column(name = "teacher_id", nullable = false)
    private Long teacherId;

    @Column(name = "subject_master_id", nullable = false)
    private Long subjectMasterId;

    @Enumerated(EnumType.STRING)
    @Column(name = "subject_type", nullable = false)
    private SubjectType subjectType = SubjectType.ELECTIVE;

    @Column(name = "subject_target_grade", nullable = false)
    private String subjectTargetGrade;

    @Enumerated(EnumType.STRING)
    @Column(name = "subject_status")
    private SubjectStatus subjectStaus = SubjectStatus.PENDING;

    @Column(name = "subject_max_enrollment", nullable = false)
    private Integer subjectMaxEnrollment;

    @Enumerated(EnumType.STRING)
    @Column(name = "subject_semester", nullable = false)
    private SubjectSemester subjectSemester;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
