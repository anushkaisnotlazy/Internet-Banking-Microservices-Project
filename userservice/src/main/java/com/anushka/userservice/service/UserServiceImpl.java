package com.anushka.userservice.service;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.anushka.userservice.dto.AccountDTO;
import com.anushka.userservice.dto.UserDTO;
import com.anushka.userservice.exceptions.UserNotFoundException;
import com.anushka.userservice.model.User;
import com.anushka.userservice.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

//	@Autowired
//	private RestTemplate restTemplate;

	//private static final String ACCOUNT_SERVICE_URL = "http://localhost:8082/accounts/create"; // Account_Service

	@Override
	public User registerUser(UserDTO userDTO) {
		User user = new User();
		user.setName(userDTO.getName());
		user.setMobileNumber(userDTO.getMobileNumber());
		user.setEmail(userDTO.getEmail());
		user.setAge(userDTO.getAge());
		user.setAccountNumber(generateUniqueAccountNumber());
		User savedUser = userRepository.save(user);

		// Create account for the user in Account_Service
		createAccountForUser(savedUser);

		return savedUser;
	}

	private void createAccountForUser(User user) {
		AccountDTO accountDTO = new AccountDTO(user.getId(), user.getAccountNumber(), 0);
		//restTemplate.postForObject(ACCOUNT_SERVICE_URL, accountDTO, Void.class);
	}

	@Override
	public Optional<User> getUserById(Long id) {
		return Optional.ofNullable(userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User not found with id: " + id)));
	}

	@Override
	public User updateUser(Long id, UserDTO userDTO) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
		user.setName(userDTO.getName());
		user.setMobileNumber(userDTO.getMobileNumber());
		user.setEmail(userDTO.getEmail());
		user.setAge(userDTO.getAge());
		return userRepository.save(user);
	}

	@Override
	public AccountDTO getAccountDTOByUserId(Long userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
		return new AccountDTO(user.getId(), user.getAccountNumber(), userId);
	}

	private String generateUniqueAccountNumber() {
		Random random = new Random();
		StringBuilder accountNumber = new StringBuilder();
		for (int i = 0; i < 12; i++) {
			accountNumber.append(random.nextInt(10));
		}
		return accountNumber.toString();
	}

}