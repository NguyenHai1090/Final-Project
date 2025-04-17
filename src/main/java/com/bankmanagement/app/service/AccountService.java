package com.bankmanagement.app.service;

import com.bankmanagement.app.dto.AccountDTO;
import com.bankmanagement.app.dto.CreditAccountDTO;
import com.bankmanagement.app.dto.DepositAccountDTO;
import com.bankmanagement.app.model.*;
import com.bankmanagement.app.repository.AccountRepository;
import com.bankmanagement.app.repository.CreditAccountRepository;
import com.bankmanagement.app.repository.CustomerRepository;
import com.bankmanagement.app.repository.DepositAccountRepository;
import com.bankmanagement.app.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CreditAccountRepository creditAccountRepository;

    @Autowired
    private DepositAccountRepository depositAccountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional(readOnly = true)
    public List<AccountDTO> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AccountDTO getAccountByNumber(String accountNumber) {
        return accountRepository.findById(accountNumber)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public List<AccountDTO> getAccountsByCustomerId(String customerId) {
        return accountRepository.findByCustomerCustomerId(customerId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public CreditAccountDTO createCreditAccount(CreditAccountDTO accountDTO) {
        Customer customer = customerRepository.findById(accountDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Employee employee = employeeRepository.findById(accountDTO.getCreatedByEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        CreditAccount account = new CreditAccount();
        account.setAccountNumber(accountDTO.getAccountNumber());
        account.setCustomer(customer);
        account.setAccountType(Account.AccountType.CREDIT);
        account.setBalance(BigDecimal.ZERO);
        account.setCreatedDate(LocalDateTime.now());
        account.setCreatedByEmployee(employee);
        account.setStatus(Account.AccountStatus.ACTIVE);
        account.setCreditLimit(accountDTO.getCreditLimit());
        account.setCurrentDebt(BigDecimal.ZERO);

        CreditAccount savedAccount = creditAccountRepository.save(account);
        return convertToCreditAccountDTO(savedAccount);
    }

    @Transactional
    public DepositAccountDTO createDepositAccount(DepositAccountDTO accountDTO) {
        Customer customer = customerRepository.findById(accountDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        Employee employee = employeeRepository.findById(accountDTO.getCreatedByEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        DepositAccount account = new DepositAccount();
        account.setAccountNumber(accountDTO.getAccountNumber());
        account.setCustomer(customer);
        account.setAccountType(Account.AccountType.DEPOSIT);
        account.setBalance(accountDTO.getBalance());
        account.setCreatedDate(LocalDateTime.now());
        account.setCreatedByEmployee(employee);
        account.setStatus(Account.AccountStatus.ACTIVE);
        account.setInterestRate(accountDTO.getInterestRate());
        account.setMinBalance(accountDTO.getMinBalance());

        DepositAccount savedAccount = depositAccountRepository.save(account);
        return convertToDepositAccountDTO(savedAccount);
    }

    @Transactional
    public void closeAccount(String accountNumber) {
        Account account = accountRepository.findById(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        account.setStatus(Account.AccountStatus.CLOSED);
        accountRepository.save(account);
    }

    private AccountDTO convertToDTO(Account account) {
        AccountDTO dto = new AccountDTO();
        dto.setAccountNumber(account.getAccountNumber());
        dto.setCustomerId(account.getCustomer().getCustomerId());
        dto.setAccountType(account.getAccountType());
        dto.setBalance(account.getBalance());
        dto.setCreatedDate(account.getCreatedDate());
        dto.setCreatedByEmployeeId(account.getCreatedByEmployee().getEmployeeId());
        dto.setStatus(account.getStatus());
        return dto;
    }

    private CreditAccountDTO convertToCreditAccountDTO(CreditAccount account) {
        CreditAccountDTO dto = new CreditAccountDTO();
        dto.setAccountNumber(account.getAccountNumber());
        dto.setCustomerId(account.getCustomer().getCustomerId());
        dto.setAccountType(account.getAccountType());
        dto.setBalance(account.getBalance());
        dto.setCreatedDate(account.getCreatedDate());
        dto.setCreatedByEmployeeId(account.getCreatedByEmployee().getEmployeeId());
        dto.setStatus(account.getStatus());
        dto.setCreditLimit(account.getCreditLimit());
        dto.setCurrentDebt(account.getCurrentDebt());
        return dto;
    }

    private DepositAccountDTO convertToDepositAccountDTO(DepositAccount account) {
        DepositAccountDTO dto = new DepositAccountDTO();
        dto.setAccountNumber(account.getAccountNumber());
        dto.setCustomerId(account.getCustomer().getCustomerId());
        dto.setAccountType(account.getAccountType());
        dto.setBalance(account.getBalance());
        dto.setCreatedDate(account.getCreatedDate());
        dto.setCreatedByEmployeeId(account.getCreatedByEmployee().getEmployeeId());
        dto.setStatus(account.getStatus());
        dto.setInterestRate(account.getInterestRate());
        dto.setMinBalance(account.getMinBalance());
        return dto;
    }
} 