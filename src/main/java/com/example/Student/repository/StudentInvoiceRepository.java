package com.example.Student.repository;

import com.example.Student.model.StudentInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentInvoiceRepository extends JpaRepository<StudentInvoice, Long> {
}
