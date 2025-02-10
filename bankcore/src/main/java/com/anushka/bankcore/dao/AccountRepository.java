package com.anushka.bankcore.dao;

import com.anushka.bankcore.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repository interface for managing Account entities. Extends JpaRepository to
 * provide CRUD operations.
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

	/**
	 * Finds an account by its account number.
	 * 
	 * @param accountNumber the account number
	 * @return an Optional containing the account if found, or an empty Optional if
	 *         not found
	 */
	Optional<Account> findByAccountNumber(String accountNumber);
}
