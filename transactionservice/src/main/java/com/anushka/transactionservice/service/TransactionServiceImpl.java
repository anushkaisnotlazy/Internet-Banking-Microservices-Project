package com.anushka.transactionservice.service;

import com.anushka.transactionservice.dto.TransactionDTO;
import com.anushka.transactionservice.exception.InsufficientBalanceException;
import com.anushka.transactionservice.model.Transaction;
import com.anushka.transactionservice.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
 
import java.time.LocalDateTime;
import java.util.List;
 
@Service
public class TransactionServiceImpl implements TransactionService {
 
    @Autowired
    private TransactionRepository transactionRepository;
 
    @Autowired
    private RestTemplate restTemplate;
 
private static final String BANKCORE_SERVICE_URL = "http://localhost:8081/api/accounts";
 
    @Override
    public Transaction addMoney(TransactionDTO transactionDTO) {
        Transaction transaction = new Transaction();
        transaction.setAccountNumber(transactionDTO.getAccountNumber());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setType("DEPOSIT");
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);
 
        // Update balance in BankCore service
        restTemplate.put(BANKCORE_SERVICE_URL + "/addBalance/" + transactionDTO.getAccountNumber() + "/" + transactionDTO.getAmount(), null);
 
        return transaction;
    }
 
    @Override
    public Transaction withdrawMoney(TransactionDTO transactionDTO) {
        // Fetch account balance
        double balance = restTemplate.getForObject(BANKCORE_SERVICE_URL + "/balance/" + transactionDTO.getAccountNumber(), Double.class);
 
        if (transactionDTO.getAmount() > balance) {
            throw new InsufficientBalanceException("Insufficient balance for withdrawal.");
        }
 
        Transaction transaction = new Transaction();
        transaction.setAccountNumber(transactionDTO.getAccountNumber());
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setType("WITHDRAWAL");
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);
 
        // Update balance in BankCore service
        restTemplate.put(BANKCORE_SERVICE_URL + "/deductBalance/" + transactionDTO.getAccountNumber() + "/" + transactionDTO.getAmount(), null);
 
        return transaction;
    }
 
    @Override
    public List<Transaction> getTransactionHistory(String accountNumber) {
        return transactionRepository.findByAccountNumber(accountNumber);
    }
}