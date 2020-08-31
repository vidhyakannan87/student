package com.example.Student.repository;

import com.example.Student.model.Interests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestRepository extends JpaRepository<Interests,Long> {
}
