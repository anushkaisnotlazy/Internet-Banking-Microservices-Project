package com.anushka.transactionservice.controller;

import com.anushka.transactionservice.dto.TransactionDTO;
import com.anushka.transactionservice.model.Transaction;
import com.anushka.transactionservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions") // Base path for all transaction-related APIs
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	// Endpoint to add money to an account
	@PostMapping("/addMoney") // URL: http://localhost:8082/transactions/addMoney
	public ResponseEntity<Transaction> addMoney(@RequestBody TransactionDTO transactionDTO) {
		Transaction transaction = transactionService.addMoney(transactionDTO);
		return ResponseEntity.ok(transaction);
	}

	// Endpoint to withdraw money from an account
	@PostMapping("/withdraw") // URL: http://localhost:8082/transactions/withdraw
	public ResponseEntity<Transaction> withdrawMoney(@RequestBody TransactionDTO transactionDTO) {
		Transaction transaction = transactionService.withdrawMoney(transactionDTO);
		return ResponseEntity.ok(transaction);
	}

	// Endpoint to fetch transaction history
	@GetMapping("/{accountNumber}") // URL: http://localhost:8082/transactions/{accountNumber}
	public ResponseEntity<List<Transaction>> getTransactionHistory(@PathVariable String accountNumber) {
		List<Transaction> transactions = transactionService.getTransactionHistory(accountNumber);
		return ResponseEntity.ok(transactions);
	}
}