package com.anushka.bankcore.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anushka.bankcore.dao.AccountRepository;
import com.anushka.bankcore.exceptions.ResourceNotFoundException;
import com.anushka.bankcore.model.Account;

/**
 * Service implementation for managing Account entities. Implements the methods
 * defined in the AccountService interface.
 */
@Service
public class AccountServiceImpl implements AccountService {

	private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public Account createAccount(Account account) {
		logger.info("Creating new account: {}", account);
		return accountRepository.save(account);
	}

	@Override
	public List<Account> getAllAccounts() {
		logger.info("Fetching all accounts");
		return accountRepository.findAll();
	}

	@Override
	public Optional<Account> getAccountByNumber(String accountNumber) {
		logger.info("Fetching account with account number: {}", accountNumber);
		return Optional.ofNullable(accountRepository.findByAccountNumber(accountNumber)).orElseThrow(() -> {
			logger.error("Account not found with account number: {}", accountNumber);
			return new ResourceNotFoundException("Account not found with account number: " + accountNumber);
		});
	}

	@Override
	public Account getAccountById(Long id) {
		logger.info("Fetching account with ID: {}", id);
		return accountRepository.findById(id).orElseThrow(() -> {
			logger.error("Account not found with ID: {}", id);
			return new ResourceNotFoundException("Account not found with id: " + id);
		});
	}

	@Override
	public void deleteAccount(Long id) {
		logger.info("Deleting account with ID: {}", id);
		if (!accountRepository.existsById(id)) {
			logger.error("Account not found with ID: {}", id);
			throw new ResourceNotFoundException("Account not found with id: " + id);
		}
		accountRepository.deleteById(id);
		logger.info("Account with ID {} deleted successfully", id);
	}

	@Override
	public Account save(Account account) {
		logger.info("Saving account: {}", account);
		return accountRepository.save(account);
	}
}