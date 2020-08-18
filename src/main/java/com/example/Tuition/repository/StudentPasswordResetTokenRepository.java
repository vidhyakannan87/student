package com.example.Tuition.repository;

import com.example.Tuition.model.StudentPasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentPasswordResetTokenRepository extends JpaRepository<StudentPasswordResetToken, Long> {

  StudentPasswordResetToken findByToken(String token);
}
