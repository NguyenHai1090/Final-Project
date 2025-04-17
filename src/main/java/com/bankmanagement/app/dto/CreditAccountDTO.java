package com.bankmanagement.app.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class CreditAccountDTO extends AccountDTO {
    private BigDecimal creditLimit;
    private BigDecimal currentDebt;
} 