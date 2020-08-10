package com.example.Tuition.repository;

import com.example.Tuition.model.Interests;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestRepository extends JpaRepository<Interests,Long> {
}
