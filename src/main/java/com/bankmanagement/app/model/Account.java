package com.bankmanagement.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Account")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    
    @Id
    @Column(name = "account_number")
    private String accountNumber;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false)
    private AccountType accountType;
    
    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;
    
    @Column(name = "created_date")
    private LocalDateTime createdDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_employee_id", nullable = false)
    private Employee createdByEmployee;
    
    @Enumerated(EnumType.STRING)
    @Column
    private AccountStatus status = AccountStatus.ACTIVE;
    
    public enum AccountType {
        CREDIT, DEPOSIT
    }
    
    public enum AccountStatus {
        ACTIVE, INACTIVE, CLOSED
    }
} 