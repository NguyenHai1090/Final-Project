package com.bankmanagement.app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "DepositAccount")
@PrimaryKeyJoinColumn(name = "account_number")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class DepositAccount extends Account {
    
    @Column(name = "interest_rate", nullable = false)
    private BigDecimal interestRate;
    
    @Column(name = "min_balance", nullable = false)
    private BigDecimal minBalance = BigDecimal.ZERO;
} 