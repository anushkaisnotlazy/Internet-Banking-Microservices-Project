package com.anushka.transactionservice.controller;

import com.anushka.transactionservice.dto.TransactionDTO;
import com.anushka.transactionservice.model.Transaction;
import com.anushka.transactionservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@PostMapping("/addMoney")
	public Transaction addMoney(@RequestBody TransactionDTO transactionDTO) {
		return transactionService.addMoney(transactionDTO);
	}

	@PostMapping("/withdraw")
	public Transaction withdrawMoney(@RequestBody TransactionDTO transactionDTO) {
		return transactionService.withdrawMoney(transactionDTO);
	}

	@GetMapping("/{accountNumber}")
	public List<Transaction> getTransactionHistory(@PathVariable String accountNumber) {
		return transactionService.getTransactionHistory(accountNumber);
	}
}