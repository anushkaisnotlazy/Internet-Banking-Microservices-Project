package com.anushka.transactionservice.service;

import com.anushka.transactionservice.dto.TransactionDTO;
import com.anushka.transactionservice.model.Transaction;

import java.util.List;

public interface TransactionService {
	Transaction addMoney(TransactionDTO transactionDTO);

	Transaction withdrawMoney(TransactionDTO transactionDTO);

	List<Transaction> getTransactionHistory(String accountNumber);
}