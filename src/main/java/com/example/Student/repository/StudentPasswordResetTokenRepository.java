package com.example.Student.repository;

import com.example.Student.model.StudentPasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentPasswordResetTokenRepository extends JpaRepository<StudentPasswordResetToken, Long> {

  StudentPasswordResetToken findByToken(String token);
}
