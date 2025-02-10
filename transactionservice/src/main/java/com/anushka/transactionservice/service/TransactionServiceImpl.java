package com.anushka.transactionservice.service;

import com.anushka.transactionservice.dto.TransactionDTO;
import com.anushka.transactionservice.exception.InsufficientBalanceException;
import com.anushka.transactionservice.model.Transaction;
import com.anushka.transactionservice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Implementation of TransactionService that handles business logic for
 * transactions.
 */
@Service
public class TransactionServiceImpl implements TransactionService {
	
    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);


	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private RestTemplate restTemplate;

	private static final String BANKCORE_SERVICE_URL = "http://localhost:8081/accounts";

	/**
	 * Adds money to a specified account and updates the balance in the BankCore
	 * service.
	 *
	 * @param transactionDTO contains account number and amount
	 * @return the saved transaction record
	 */
	@Override
    public Transaction addMoney(TransactionDTO transactionDTO) {
        logger.info("Adding money to account: {}", transactionDTO.getAccountNumber());
        Transaction transaction = new Transaction();
        transaction.setAccountNumber(transactionDTO.getAccountNumber());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setType("DEPOSIT");
        transaction.setTimestamp(LocalDateTime.now());

        transactionRepository.save(transaction);
        
		// Update balance in BankCore service
		restTemplate.put(BANKCORE_SERVICE_URL + "/addBalance/" + transactionDTO.getAccountNumber() + "/"
				+ transactionDTO.getAmount(), null);
        logger.info("Money added to account: {}", transactionDTO.getAccountNumber());

		return transaction;
	}

	/**
	 * Withdraws money from a specified account and updates the balance in the
	 * BankCore service.
	 *
	 * @param transactionDTO contains account number and amount
	 * @return the saved transaction record
	 * @throws InsufficientBalanceException if the withdrawal amount exceeds the
	 *                                      available balance
	 */
	@Override
    public Transaction withdrawMoney(TransactionDTO transactionDTO) {
        logger.info("Withdrawing money from account: {}", transactionDTO.getAccountNumber());
        // Fetch account balance from BankCore service
        Double balance = restTemplate
                .getForObject(BANKCORE_SERVICE_URL + "/balance/" + transactionDTO.getAccountNumber(), Double.class);

        if (balance == null || transactionDTO.getAmount() > balance) {
            logger.error("Insufficient balance for withdrawal from account: {}", transactionDTO.getAccountNumber());
            throw new InsufficientBalanceException("Insufficient balance for withdrawal.");
        }

		Transaction transaction = new Transaction();
		transaction.setAccountNumber(transactionDTO.getAccountNumber());
		transaction.setAmount(transactionDTO.getAmount());
		transaction.setType("WITHDRAWAL");
		transaction.setTimestamp(LocalDateTime.now());

		transactionRepository.save(transaction);

		// Deduct balance in BankCore service
		try {
		restTemplate.put(BANKCORE_SERVICE_URL + "/deductBalance/" + transactionDTO.getAccountNumber() + "/"
				+ transactionDTO.getAmount(), null);
		}catch(HttpClientErrorException | HttpServerErrorException e) {
			throw new RuntimeException("Error while calling BankCoreService: " + e.getMessage());
		}

		return transaction;
	}

	/**
	 * Retrieves the transaction history for a specified account.
	 *
	 * @param accountNumber the account number
	 * @return a list of transactions for the given account
	 */
	@Override
	public List<Transaction> getTransactionHistory(String accountNumber) {
		return transactionRepository.findByAccountNumber(accountNumber);
	}
}
