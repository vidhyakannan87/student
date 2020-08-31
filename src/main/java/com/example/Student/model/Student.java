package com.example.Student.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Student {

  private static final String NAME = "student_id_sequence";
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = NAME)
  @SequenceGenerator(
          name = NAME,
          sequenceName = NAME,
          initialValue = BaseEntity.INITIAL_VALUE,
          allocationSize = BaseEntity.ALLOCATION_SIZE
  )
  @JsonIgnore
  @Id
  private long id;

  private String firstName;

  private String lastName;

  @Column(unique = true)
  private String userName;

  @Column(unique = true)
  private String uuid;

  @JsonIgnore
  private String externalId;

  @Column(unique = true)
  private String email;

  private EducationBoard educationBoard;

  private String phoneNumber;

  @JsonIgnore
  private String stripeCustomerId;

  @JsonIgnore
  private String razorPayCustomerId;

  private LocalDate dateOfBirth;

  @OneToMany(mappedBy = "student", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private List<Interests> interests = new ArrayList<>();

  @OneToMany(mappedBy = "student", orphanRemoval = true, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private List<StudentInvoice> studentInvoices = new ArrayList<>();

  private String profilePicUrl;

  public void setInterests(List<Interests> interests) {
    this.interests.clear();
    this.interests.addAll(interests);
  }

  public void setStudentInvoices(List<StudentInvoice> invoices){
    this.studentInvoices.clear();
    this.setStudentInvoices(invoices);
  }

}
