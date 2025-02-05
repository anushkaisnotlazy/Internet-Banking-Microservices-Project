package com.anushka.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {

	public AccountDTO(Long userId, String accountNumber, double amount) {
		super();
		this.userId = userId;
		this.accountNumber = accountNumber;
		this.amount = amount;
	}

	private Long userId;
	private String accountNumber;
	private double amount;
}