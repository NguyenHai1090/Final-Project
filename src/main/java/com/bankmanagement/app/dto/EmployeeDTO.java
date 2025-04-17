package com.bankmanagement.app.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private String employeeId;
    private String idCard;
    private String name;
    private LocalDate dob;
    private String address;
    private String skillLevel;
    private Integer seniority;
    private String position;
} 