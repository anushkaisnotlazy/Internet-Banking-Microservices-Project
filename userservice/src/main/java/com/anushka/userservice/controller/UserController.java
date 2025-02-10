package com.anushka.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anushka.userservice.dto.AccountDTO;
import com.anushka.userservice.dto.UserDTO;
import com.anushka.userservice.model.User;
import com.anushka.userservice.service.UserService;
import com.anushka.userservice.exceptions.UserNotFoundException;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/register") // http://localhost:8083/users/register
	public ResponseEntity<User> registerUser(@RequestBody UserDTO userDTO) {
		User user = userService.registerUser(userDTO);
		return ResponseEntity.ok(user);
	}

	@GetMapping("/{id}") // http://localhost:8083/users/{id}
	public ResponseEntity<User> getUserById(@PathVariable String id) {
		try {
			Long userId = Long.parseLong(id);
			User user = userService.getUserById(userId)
					.orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
			return ResponseEntity.ok(user);
		} catch (NumberFormatException e) {
			throw new UserNotFoundException("Invalid user id: " + id);
		}
	}

	@GetMapping("/accountNumber/{userId}") // http://localhost:8083/users/accountNumber/{userId}
	public ResponseEntity<AccountDTO> getAccountDTO(@PathVariable Long userId) {
		AccountDTO accountDTO = userService.getAccountDTOByUserId(userId);
		return ResponseEntity.ok(accountDTO);
	}

	@PutMapping("/{id}")// http://localhost:8083/users/{id}
	public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody UserDTO userDTO) {
		try {
			Long userId = Long.parseLong(id);
			User updatedUser = userService.updateUser(userId, userDTO);
			return ResponseEntity.ok(updatedUser);
		} catch (NumberFormatException e) {
			throw new UserNotFoundException("Invalid user id: " + id);
		}
	}
}
