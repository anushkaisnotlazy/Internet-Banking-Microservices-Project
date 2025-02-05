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
 
@RestController
@RequestMapping("/accounts")
public class AccountController {
 
    @Autowired
    private AccountService accountService;
 
    @PostMapping("/register")//http://localhost:8081/accounts/register
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        return ResponseEntity.ok(accountService.createAccount(account));
    }
    
    @GetMapping("/details/{id}")//http://localhost:8081/accounts/details/{id}
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        Account account = accountService.getAccountById(id);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }
 
    @GetMapping
    public ResponseEntity<List<Account>> getAllAccounts() {
        return ResponseEntity.ok(accountService.getAllAccounts());
    }
 
    @GetMapping("/{accountNumber}")//http://localhost:8081/accounts/{accountNumber}
    public ResponseEntity<Optional<Account>> getAccountByNumber(@PathVariable String accountNumber) {
    	Optional<Account> account = accountService.getAccountByNumber(accountNumber);
    	if(account==null) {
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    	}
        return ResponseEntity.ok(account);
    }
    @DeleteMapping("/remove/{id}")//http://localhost:8081/accounts/remove/{id}
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return new ResponseEntity<>("Account deleted successfully!", HttpStatus.OK);
    }
   
}