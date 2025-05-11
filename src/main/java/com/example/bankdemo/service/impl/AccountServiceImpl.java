package com.example.bankdemo.service.impl;

import com.example.bankdemo.dto.AccountDto;
import com.example.bankdemo.entity.Account;
import com.example.bankdemo.exception.NotFoundException;
import com.example.bankdemo.repository.AccountRepository;
import com.example.bankdemo.service.AccountService;
import com.example.bankdemo.mapper.AccountMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final Lock lock = new ReentrantLock();

    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public Account create(Account account) {
        account.setInitialBalance(account.getBalance());
        return accountRepository.save(account);
    }

    @Override
    @Cacheable("accounts")
    public List<AccountDto> getByUser(Long userId) {
        return accountRepository.findAll().stream()
                .filter(a -> a.getUser().getId().equals(userId))
                .map(accountMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void transfer(Long fromId, Long toId, double amount) {
        lock.lock();
        try {
            Account from = accountRepository.findById(fromId).orElseThrow(() -> new NotFoundException("From account not found"));
            Account to = accountRepository.findById(toId).orElseThrow(() -> new NotFoundException("To account not found"));
            BigDecimal amt = BigDecimal.valueOf(amount);
            from.setBalance(from.getBalance().subtract(amt));
            to.setBalance(to.getBalance().add(amt));
            accountRepository.save(from);
            accountRepository.save(to);
        } finally {
            lock.unlock();
        }
    }
}
