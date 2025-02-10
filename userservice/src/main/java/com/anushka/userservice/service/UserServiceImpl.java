package com.anushka.userservice.service;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.anushka.userservice.dto.AccountDTO;
import com.anushka.userservice.dto.UserDTO;
import com.anushka.userservice.exceptions.UserNotFoundException;
import com.anushka.userservice.feign.BankCoreClient;
import com.anushka.userservice.model.User;
import com.anushka.userservice.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BankCoreClient bankCoreClient;

	@Override
	public UserDTO getUserWithAccountDetails(Long userId) {
		logger.info("Fetching user with account details for user ID: {}", userId);
		Optional<User> user = userRepository.findById(userId);

		if (user.isPresent()) {
			User foundUser = user.get();
			AccountDTO accountDTO = bankCoreClient.getAccountByNumber(foundUser.getAccountNumber());
			logger.debug("User found: {}", foundUser);
			return new UserDTO(foundUser.getName(), foundUser.getEmail(), accountDTO);
		} else {
			logger.error("User not found with ID: {}", userId);
			throw new RuntimeException("User not found with ID: " + userId);
		}
	}

//	@Autowired
//	private RestTemplate restTemplate;

	// private static final String ACCOUNT_SERVICE_URL =
	// "http://localhost:8082/accounts/create"; // Account_Service

	@Override
	public User registerUser(UserDTO userDTO) {
		User user = new User();
		user.setName(userDTO.getName());
		user.setMobileNumber(userDTO.getMobileNumber());
		user.setEmail(userDTO.getEmail());
		user.setAge(userDTO.getAge());
		user.setAccountNumber(generateUniqueAccountNumber());
		User savedUser = userRepository.save(user);
		logger.debug("User registered with ID: {}", savedUser.getId());

		// Create account for the user in Account_Service
		createAccountForUser(savedUser);

		return savedUser;
	}

	private void createAccountForUser(User user) {
		AccountDTO accountDTO = new AccountDTO(user.getId(), user.getAccountNumber(), 0);
		// restTemplate.postForObject(ACCOUNT_SERVICE_URL, accountDTO, Void.class);
	}

	@Override
	public Optional<User> getUserById(Long id) {
		logger.info("Fetching user with ID: {}", id);
		return Optional.ofNullable(userRepository.findById(id).orElseThrow(() -> {
			logger.error("User not found with ID: {}", id);
			return new UserNotFoundException("User not found with id: " + id);
		}));
	}

	@Override
	public User updateUser(Long id, UserDTO userDTO) {
		logger.info("Updating user with ID: {}", id);
		User user = userRepository.findById(id).orElseThrow(() -> {
			logger.error("User not found with ID: {}", id);
			return new UserNotFoundException("User not found with id: " + id);
		});
		user.setName(userDTO.getName());
		user.setMobileNumber(userDTO.getMobileNumber());
		user.setEmail(userDTO.getEmail());
		user.setAge(userDTO.getAge());
		User updatedUser = userRepository.save(user);
		logger.debug("User updated: {}", updatedUser);
		return userRepository.save(user);
	}

	@Override
	public AccountDTO getAccountDTOByUserId(Long userId) {
		logger.info("Fetching account details for user ID: {}", userId);
		User user = userRepository.findById(userId).orElseThrow(() -> {
			logger.error("User not found with ID: {}", userId);
			return new UserNotFoundException("User not found with id: " + userId);
		});
		return new AccountDTO(user.getId(), user.getAccountNumber(), userId);
	}

	private String generateUniqueAccountNumber() {
		Random random = new Random();
		StringBuilder accountNumber = new StringBuilder();
		for (int i = 0; i < 12; i++) {
			accountNumber.append(random.nextInt(10));
		}
		logger.debug("Generated unique account number: {}", accountNumber);
		return accountNumber.toString();
	}
}