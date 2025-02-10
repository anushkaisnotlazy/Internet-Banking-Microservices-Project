package com.anushka.userservice.feign;

import com.anushka.userservice.dto.AccountDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
 
@FeignClient(name = "bankcore-service", path = "/accounts")  // BankCore microservice
public interface BankCoreClient {
 
    @GetMapping("/{accountNumber}")
    AccountDTO getAccountByNumber(@PathVariable("accountNumber") String accountNumber);
}
