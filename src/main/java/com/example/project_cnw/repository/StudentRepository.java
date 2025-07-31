package com.example.project_cnw.repository;

import com.example.project_cnw.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
