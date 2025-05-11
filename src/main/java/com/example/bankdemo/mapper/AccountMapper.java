package com.example.bankdemo.mapper;

import com.example.bankdemo.dto.AccountDto;
import com.example.bankdemo.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountDto toDto(Account account);
}
