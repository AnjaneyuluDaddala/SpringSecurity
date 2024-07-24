package com.example.swagger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.swagger.model.Student;
import java.util.Optional;

@Repository
public interface studentRepository extends JpaRepository<Student,Long>{

    Student findByEmail(String email);
    Optional<Student> findById(Long id);

}
