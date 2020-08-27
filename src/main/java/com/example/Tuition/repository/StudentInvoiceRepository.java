package com.example.Tuition.repository;

import com.example.Tuition.model.StudentInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentInvoiceRepository extends JpaRepository<StudentInvoice, Long> {
}
