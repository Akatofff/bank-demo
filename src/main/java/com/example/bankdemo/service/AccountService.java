package com.example.bankdemo.service;

import com.example.bankdemo.dto.AccountDto;
import com.example.bankdemo.entity.Account;

import java.util.List;

public interface AccountService {
    Account create(Account account);
    List<AccountDto> getByUser(Long userId);
    void transfer(Long fromId, Long toId, double amount);
}
