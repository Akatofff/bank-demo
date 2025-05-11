package com.example.bankdemo.service.impl;

import com.example.bankdemo.dto.UserDto;
import com.example.bankdemo.entity.User;
import com.example.bankdemo.mapper.UserMapper;
import com.example.bankdemo.repository.UserRepository;
import com.example.bankdemo.service.UserService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    @Cacheable("users")
    public List<UserDto> getAll() {
        return userRepository.findAll().stream().map(userMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "user", key = "#id")
    public UserDto getById(Long id) {
        return userMapper.toDto(userRepository.findById(id).orElseThrow());
    }
}
