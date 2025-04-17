package com.bankmanagement.app.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_account_number")
    private Account sourceAccount;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "destination_account_number")
    private Account destinationAccount;
    
    @Column(nullable = false)
    private BigDecimal amount;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type", nullable = false)
    private TransactionType transactionType;
    
    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;
    
    @Column
    private String description;
    
    public enum TransactionType {
        DEPOSIT, WITHDRAWAL, TRANSFER, CREDIT_PAYMENT, CREDIT_USAGE
    }
} 