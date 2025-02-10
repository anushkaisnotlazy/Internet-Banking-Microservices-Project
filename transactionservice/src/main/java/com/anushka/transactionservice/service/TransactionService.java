package com.anushka.transactionservice.service;

import com.anushka.transactionservice.dto.TransactionDTO;
import com.anushka.transactionservice.model.Transaction;

import java.util.List;

/**
 * Service interface for managing transactions. Defines operations for adding
 * money, withdrawing money, and retrieving transaction history.
 */
public interface TransactionService {

	/**
	 * Adds money to a specific account.
	 *
	 * @param transactionDTO the transaction details containing account number and
	 *                       amount
	 * @return the transaction record after being saved in the database
	 */
	Transaction addMoney(TransactionDTO transactionDTO);

	/**
	 * Withdraws money from a specific account.
	 *
	 * @param transactionDTO the transaction details containing account number and
	 *                       amount
	 * @return the transaction record after being saved in the database
	 * @throws InsufficientBalanceException if the account balance is insufficient
	 */
	Transaction withdrawMoney(TransactionDTO transactionDTO);

	/**
	 * Retrieves the transaction history for a given account.
	 *
	 * @param accountNumber the account number to fetch transaction history
	 * @return a list of transactions associated with the given account
	 */
	List<Transaction> getTransactionHistory(String accountNumber);
}
