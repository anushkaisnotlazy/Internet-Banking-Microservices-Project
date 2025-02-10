package com.anushka.bankcore.service;

import com.anushka.bankcore.model.Account;

import java.util.List;
import java.util.Optional;
 
public interface AccountService {
    Account createAccount(Account account);
    List<Account> getAllAccounts();
    Optional<Account> getAccountByNumber(String accountNumber);
	Account getAccountById(Long id);
	void deleteAccount(Long id);
	Account save(Account account);
}