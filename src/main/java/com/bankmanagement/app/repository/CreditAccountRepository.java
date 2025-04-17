package com.bankmanagement.app.repository;

import com.bankmanagement.app.model.CreditAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditAccountRepository extends JpaRepository<CreditAccount, String> {
    List<CreditAccount> findByCustomerCustomerId(String customerId);
} 