package com.bankmanagement.app.dto;

import com.bankmanagement.app.model.Account.AccountStatus;
import com.bankmanagement.app.model.Account.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private String accountNumber;
    private String customerId;
    private AccountType accountType;
    private BigDecimal balance;
    private LocalDateTime createdDate;
    private String createdByEmployeeId;
    private AccountStatus status;
} 