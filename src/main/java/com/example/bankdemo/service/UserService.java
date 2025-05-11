package com.example.bankdemo.service;

import com.example.bankdemo.dto.UserDto;
import com.example.bankdemo.entity.User;

import java.util.List;

public interface UserService {
    User create(User user);
    List<UserDto> getAll();
    UserDto getById(Long id);
}
