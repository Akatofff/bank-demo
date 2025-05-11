package com.example.bankdemo.config;

import com.example.bankdemo.repository.AccountRepository;
import com.example.bankdemo.entity.Account;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class SchedulerConfig {
    private final AccountRepository accountRepository;

    public SchedulerConfig(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Scheduled(fixedRate = 30000)
    public void updateBalances() {
        for (Account account : accountRepository.findAll()) {
            BigDecimal cap = account.getInitialBalance().multiply(BigDecimal.valueOf(2.07));
            BigDecimal increment = account.getBalance().multiply(BigDecimal.valueOf(0.1));
            BigDecimal newBalance = account.getBalance().add(increment);
            if (newBalance.compareTo(cap) > 0) {
                newBalance = cap;
            }
            account.setBalance(newBalance);
            accountRepository.save(account);
        }
    }
}
