package com.anushka.bankcore.service;
 
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anushka.bankcore.dao.AccountRepository;
import com.anushka.bankcore.exceptions.ResourceNotFoundException;
import com.anushka.bankcore.model.Account;
 
@Service
public class AccountServiceImpl implements AccountService {
 
    @Autowired
    private AccountRepository accountRepository;
 
    @Override
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }
 
    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
 
    @Override
    public Optional<Account> getAccountByNumber(String accountNumber) {
        return Optional.ofNullable(accountRepository.findByAccountNumber(accountNumber))
        		.orElseThrow(() -> new ResourceNotFoundException("Account not found with account number: " + accountNumber));
    }
 
    @Override
    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Account not found with id: " + id));
    }
 
    @Override
    public void deleteAccount(Long id) {
        if (!accountRepository.existsById(id)) {
            throw new ResourceNotFoundException("Account not found with id: " + id);
        }
        accountRepository.deleteById(id);
    }
}