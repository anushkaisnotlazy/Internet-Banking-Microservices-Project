package com.anushka.bankcore.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anushka.bankcore.model.Account;
import com.anushka.bankcore.service.AccountService;

/**
 * REST controller for managing accounts.
 */
@RestController
@RequestMapping("/accounts")
public class AccountController {

	@Autowired
	private AccountService accountService;

	/**
	 * Registers a new account.
	 * 
	 * @param account the account to be created
	 * @return the created account  
	 * 
	 * @param accountNumber the account number
	 * @return the account details
	 */
	@GetMapping("/{accountNumber}") // http://localhost:8081/accounts/{accountNumber}
	public ResponseEntity<Optional<Account>> getAccountByNumber(@PathVariable String accountNumber) {
		Optional<Account> account = accountService.getAccountByNumber(accountNumber);
		if (account == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return ResponseEntity.ok(account);
	}

	/**
	 * Deletes an account by ID.
	 * 
	 * @param id the ID of the account to be deleted
	 * @return a confirmation message
	 */
	@DeleteMapping("/remove/{id}") // http://localhost:8081/accounts/remove/{id}
	public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
		accountService.deleteAccount(id);
		return new ResponseEntity<>("Account deleted successfully!", HttpStatus.OK);
	}

}