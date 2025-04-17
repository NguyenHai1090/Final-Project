package com.bankmanagement.app.repository;

import com.bankmanagement.app.model.DepositAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepositAccountRepository extends JpaRepository<DepositAccount, String> {
    List<DepositAccount> findByCustomerCustomerId(String customerId);
} 