package com.anushka.userservice.repository;

import com.anushka.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByAccountNumber(String accountNumber);
}