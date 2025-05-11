package com.example.bankdemotest.service;

import com.example.bankdemo.dto.AccountDto;
import com.example.bankdemo.entity.Account;
import com.example.bankdemo.exception.NotFoundException;
import com.example.bankdemo.mapper.AccountMapper;
import com.example.bankdemo.repository.AccountRepository;
import com.example.bankdemo.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AccountServiceImplTest {

    private AccountRepository accountRepository;
    private AccountMapper accountMapper;
    private AccountServiceImpl accountService;

    @BeforeEach
    void setUp() {
        accountRepository = mock(AccountRepository.class);
        accountMapper = new AccountMapper() {
            @Override
            public AccountDto toDto(Account account) {
                return null;
            }
        };
        accountService = new AccountServiceImpl(accountRepository, accountMapper);
    }

    @Test
    void create_ShouldSetInitialAndSave() {
        Account acc = new Account();
        acc.setBalance(BigDecimal.valueOf(100));
        when(accountRepository.save(any())).thenAnswer(i -> {
            Account a = i.getArgument(0);
            a.setId(5L);
            return a;
        });

        Account persisted = accountService.create(acc);
        assertEquals(BigDecimal.valueOf(100), persisted.getInitialBalance());
        assertEquals(5L, persisted.getId());
        verify(accountRepository).save(acc);
    }

    @Test
    void transfer_ShouldMoveMoney() {
        Account from = new Account();
        from.setId(1L);
        from.setBalance(BigDecimal.valueOf(200));
        Account to = new Account();
        to.setId(2L);
        to.setBalance(BigDecimal.valueOf(50));
        when(accountRepository.findById(1L)).thenReturn(Optional.of(from));
        when(accountRepository.findById(2L)).thenReturn(Optional.of(to));

        accountService.transfer(1L, 2L, 30);

        assertEquals(BigDecimal.valueOf(170), from.getBalance());
        assertEquals(BigDecimal.valueOf(80), to.getBalance());
        verify(accountRepository, times(2)).save(any());
    }

    @Test
    void transfer_ShouldThrow_WhenMissing() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> accountService.transfer(1L, 2L, 10));
    }
}
