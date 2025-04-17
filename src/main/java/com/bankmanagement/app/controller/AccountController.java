package com.bankmanagement.app.controller;

import com.bankmanagement.app.dto.AccountDTO;
import com.bankmanagement.app.dto.CreditAccountDTO;
import com.bankmanagement.app.dto.DepositAccountDTO;
import com.bankmanagement.app.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAllAccounts() {
        List<AccountDTO> accounts = accountService.getAllAccounts();
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping("/{number}")
    public ResponseEntity<AccountDTO> getAccountByNumber(@PathVariable("number") String accountNumber) {
        AccountDTO account = accountService.getAccountByNumber(accountNumber);
        if (account != null) {
            return new ResponseEntity<>(account, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<AccountDTO>> getAccountsByCustomerId(@PathVariable String customerId) {
        List<AccountDTO> accounts = accountService.getAccountsByCustomerId(customerId);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @PostMapping("/credit")
    public ResponseEntity<CreditAccountDTO> createCreditAccount(@RequestBody CreditAccountDTO accountDTO) {
        CreditAccountDTO createdAccount = accountService.createCreditAccount(accountDTO);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    @PostMapping("/deposit")
    public ResponseEntity<DepositAccountDTO> createDepositAccount(@RequestBody DepositAccountDTO accountDTO) {
        DepositAccountDTO createdAccount = accountService.createDepositAccount(accountDTO);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    @PutMapping("/{number}/close")
    public ResponseEntity<Void> closeAccount(@PathVariable("number") String accountNumber) {
        accountService.closeAccount(accountNumber);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
} 