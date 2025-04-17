package com.bankmanagement.app.repository;

import com.bankmanagement.app.model.Account;
import com.bankmanagement.app.model.Account.AccountStatus;
import com.bankmanagement.app.model.Account.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    List<Account> findByCustomerCustomerId(String customerId);
    
    List<Account> findByCustomerCustomerIdAndAccountType(String customerId, AccountType accountType);
    
    List<Account> findByCustomerCustomerIdAndStatus(String customerId, AccountStatus status);
    
    @Query("SELECT COUNT(a) FROM Account a WHERE a.customer.customerId = ?1 AND a.accountType = ?2 AND a.status = 'ACTIVE'")
    int countActiveAccountsByCustomerIdAndType(String customerId, AccountType accountType);
} 