package com.bankmanagement.app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "Employee")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    
    @Id
    @Column(name = "employee_id")
    private String employeeId;
    
    @Column(name = "id_card", unique = true, nullable = false)
    private String idCard;
    
    @Column(nullable = false)
    private String name;
    
    @Column
    private LocalDate dob;
    
    @Column
    private String address;
    
    @Column(name = "skill_level")
    private String skillLevel;
    
    @Column
    private Integer seniority;
    
    @Column(nullable = false)
    private String position;
} 