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
@Table(name = "CreditAccount")
@PrimaryKeyJoinColumn(name = "account_number")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class CreditAccount extends Account {
    
    @Column(name = "credit_limit", nullable = false)
    private BigDecimal creditLimit;
    
    @Column(name = "current_debt", nullable = false)
    private BigDecimal currentDebt = BigDecimal.ZERO;
} 