package com.example.bankdemo.controller;

import com.example.bankdemo.dto.AccountDto;
import com.example.bankdemo.entity.Account;
import com.example.bankdemo.service.AccountService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public Account create(@RequestBody Account account) {
        return accountService.create(account);
    }

    @GetMapping("/user/{userId}")
    public List<AccountDto> getByUser(@PathVariable Long userId) {
        return accountService.getByUser(userId);
    }

    @PostMapping("/transfer")
    public void transfer(@RequestParam Long from, @RequestParam Long to, @RequestParam double amount) {
        accountService.transfer(from, to, amount);
    }
}
