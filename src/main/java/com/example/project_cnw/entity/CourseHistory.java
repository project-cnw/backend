package com.example.project_cnw.entity;

import com.example.project_cnw.common.enums.CourseHistorySemester;
import com.example.project_cnw.entity.datetime.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name ="course_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseHistory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_history_id")
    private Long courseHistoryId;

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Column(name = "lecture_id", nullable = false)
    private Long lectureId;

    @Column(name = "course_history_academic_year", nullable = false)
    private Integer courseHistoryAcademicYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "course_history_semester", nullable = false)
    private CourseHistorySemester courseHistorySemester;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
