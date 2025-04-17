package com.bankmanagement.app.service;

import com.bankmanagement.app.model.*;
import com.bankmanagement.app.repository.AccountRepository;
import com.bankmanagement.app.repository.EmployeeRepository;
import com.bankmanagement.app.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional(readOnly = true)
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Transaction> getTransactionsByAccountNumber(String accountNumber) {
        return transactionRepository.findAllByAccountNumber(accountNumber);
    }

    @Transactional(readOnly = true)
    public List<Transaction> getTransactionsByAccountNumberAndDateRange(
            String accountNumber, LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.findAllByAccountNumberAndDateRange(accountNumber, startDate, endDate);
    }

    @Transactional
    public Transaction createDeposit(String accountNumber, BigDecimal amount, String employeeId, String description) {
        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Transaction transaction = new Transaction();
        transaction.setDestinationAccount(account);
        transaction.setAmount(amount);
        transaction.setTransactionType(Transaction.TransactionType.DEPOSIT);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setEmployee(employee);
        transaction.setDescription(description);

        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction createWithdrawal(String accountNumber, BigDecimal amount, String employeeId, String description) {
        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        Transaction transaction = new Transaction();
        transaction.setSourceAccount(account);
        transaction.setAmount(amount);
        transaction.setTransactionType(Transaction.TransactionType.WITHDRAWAL);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setEmployee(employee);
        transaction.setDescription(description);

        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction createTransfer(
            String sourceAccountNumber, String destinationAccountNumber,
            BigDecimal amount, String employeeId, String description) {
        Account sourceAccount = accountRepository.findById(sourceAccountNumber)
                .orElseThrow(() -> new RuntimeException("Source account not found"));
        Account destinationAccount = accountRepository.findById(destinationAccountNumber)
                .orElseThrow(() -> new RuntimeException("Destination account not found"));
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (sourceAccount.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        Transaction transaction = new Transaction();
        transaction.setSourceAccount(sourceAccount);
        transaction.setDestinationAccount(destinationAccount);
        transaction.setAmount(amount);
        transaction.setTransactionType(Transaction.TransactionType.TRANSFER);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setEmployee(employee);
        transaction.setDescription(description);

        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction createCreditPayment(
            String sourceAccountNumber, String creditAccountNumber,
            BigDecimal amount, String employeeId, String description) {
        Account sourceAccount = accountRepository.findById(sourceAccountNumber)
                .orElseThrow(() -> new RuntimeException("Source account not found"));
        Account creditAccount = accountRepository.findById(creditAccountNumber)
                .orElseThrow(() -> new RuntimeException("Credit account not found"));
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (sourceAccount.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        Transaction transaction = new Transaction();
        transaction.setSourceAccount(sourceAccount);
        transaction.setDestinationAccount(creditAccount);
        transaction.setAmount(amount);
        transaction.setTransactionType(Transaction.TransactionType.CREDIT_PAYMENT);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setEmployee(employee);
        transaction.setDescription(description);

        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction createCreditUsage(
            String creditAccountNumber, BigDecimal amount,
            String employeeId, String description) {
        Account creditAccount = accountRepository.findById(creditAccountNumber)
                .orElseThrow(() -> new RuntimeException("Credit account not found"));
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (!(creditAccount instanceof CreditAccount)) {
            throw new RuntimeException("Account is not a credit account");
        }

        CreditAccount credit = (CreditAccount) creditAccount;
        if (credit.getCurrentDebt().add(amount).compareTo(credit.getCreditLimit()) > 0) {
            throw new RuntimeException("Transaction exceeds credit limit");
        }

        Transaction transaction = new Transaction();
        transaction.setDestinationAccount(creditAccount);
        transaction.setAmount(amount);
        transaction.setTransactionType(Transaction.TransactionType.CREDIT_USAGE);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setEmployee(employee);
        transaction.setDescription(description);

        return transactionRepository.save(transaction);
    }
} 