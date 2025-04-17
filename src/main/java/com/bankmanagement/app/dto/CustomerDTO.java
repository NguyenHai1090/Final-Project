package com.bankmanagement.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private String customerId;
    private String idCard;
    private String name;
    private LocalDate dob;
    private String address;
} 