package com.anushka.userservice.service;

import java.util.Optional;

import com.anushka.userservice.dto.AccountDTO;
import com.anushka.userservice.dto.UserDTO;
import com.anushka.userservice.model.User;

public interface UserService {
	User registerUser(UserDTO userDTO);

	Optional<User> getUserById(Long id);

	User updateUser(Long id, UserDTO userDTO);

	AccountDTO getAccountDTOByUserId(Long userId);

	UserDTO getUserWithAccountDetails(Long userId);
}