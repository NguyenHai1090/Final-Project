package com.bankmanagement.app.repository;

import com.bankmanagement.app.model.Transaction;
import com.bankmanagement.app.model.Transaction.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findBySourceAccountAccountNumber(String accountNumber);
    
    List<Transaction> findByDestinationAccountAccountNumber(String accountNumber);
    
    @Query("SELECT t FROM Transaction t WHERE " +
           "(t.sourceAccount.accountNumber = ?1 OR t.destinationAccount.accountNumber = ?1) " +
           "ORDER BY t.transactionDate DESC")
    List<Transaction> findAllByAccountNumber(String accountNumber);
    
    @Query("SELECT t FROM Transaction t WHERE " +
           "(t.sourceAccount.accountNumber = ?1 OR t.destinationAccount.accountNumber = ?1) " +
           "AND t.transactionDate BETWEEN ?2 AND ?3 " +
           "ORDER BY t.transactionDate DESC")
    List<Transaction> findAllByAccountNumberAndDateRange(String accountNumber, LocalDateTime startDate, LocalDateTime endDate);
    
    List<Transaction> findByTransactionType(TransactionType transactionType);
} 