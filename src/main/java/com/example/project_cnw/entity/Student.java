package com.example.project_cnw.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "student")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @Column(name = "student_id")
    private String studentId;

    @Column(name = "school_id", nullable = false)
    private Long schoolId;

    @Column(name = "student_username", unique = true, nullable = false)
    private String studentUsername;

    @Column(name = "student_password", nullable = false)
    private String studentPassword;

    @
}
