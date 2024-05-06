package com.example.Exam_JavaCore_Webapp;

import com.example.Exam_JavaCore_Webapp.request.StudentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<StudentRequest, Long> {
    List<StudentRequest> findByStudentNameContaining(String name);
}