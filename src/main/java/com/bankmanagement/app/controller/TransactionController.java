package com.bankmanagement.app.controller;

import com.bankmanagement.app.model.Transaction;
import com.bankmanagement.app.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<List<Transaction>> getTransactionsByAccountNumber(
            @PathVariable String accountNumber) {
        List<Transaction> transactions = transactionService.getTransactionsByAccountNumber(accountNumber);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/account/{accountNumber}/date-range")
    public ResponseEntity<List<Transaction>> getTransactionsByAccountNumberAndDateRange(
            @PathVariable String accountNumber,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<Transaction> transactions = transactionService.getTransactionsByAccountNumberAndDateRange(
                accountNumber, startDate, endDate);
        return ResponseEntity.ok(transactions);
    }

    @PostMapping("/deposit")
    public ResponseEntity<Transaction> createDeposit(
            @RequestParam String accountNumber,
            @RequestParam BigDecimal amount,
            @RequestParam String employeeId,
            @RequestParam(required = false) String description) {
        Transaction transaction = transactionService.createDeposit(
                accountNumber, amount, employeeId, description);
        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<Transaction> createWithdrawal(
            @RequestParam String accountNumber,
            @RequestParam BigDecimal amount,
            @RequestParam String employeeId,
            @RequestParam(required = false) String description) {
        Transaction transaction = transactionService.createWithdrawal(
                accountNumber, amount, employeeId, description);
        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/transfer")
    public ResponseEntity<Transaction> createTransfer(
            @RequestParam String sourceAccountNumber,
            @RequestParam String destinationAccountNumber,
            @RequestParam BigDecimal amount,
            @RequestParam String employeeId,
            @RequestParam(required = false) String description) {
        Transaction transaction = transactionService.createTransfer(
                sourceAccountNumber, destinationAccountNumber, amount, employeeId, description);
        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/credit-payment")
    public ResponseEntity<Transaction> createCreditPayment(
            @RequestParam String sourceAccountNumber,
            @RequestParam String creditAccountNumber,
            @RequestParam BigDecimal amount,
            @RequestParam String employeeId,
            @RequestParam(required = false) String description) {
        Transaction transaction = transactionService.createCreditPayment(
                sourceAccountNumber, creditAccountNumber, amount, employeeId, description);
        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/credit-usage")
    public ResponseEntity<Transaction> createCreditUsage(
            @RequestParam String creditAccountNumber,
            @RequestParam BigDecimal amount,
            @RequestParam String employeeId,
            @RequestParam(required = false) String description) {
        Transaction transaction = transactionService.createCreditUsage(
                creditAccountNumber, amount, employeeId, description);
        return ResponseEntity.ok(transaction);
    }
} 