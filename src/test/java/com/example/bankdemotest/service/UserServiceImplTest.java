package com.example.bankdemotest.service;

import com.example.bankdemo.dto.UserDto;
import com.example.bankdemo.entity.User;
import com.example.bankdemo.mapper.UserMapper;
import com.example.bankdemo.repository.UserRepository;
import com.example.bankdemo.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    private UserRepository userRepository;
    private UserMapper userMapper;
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userMapper = Mappers.getMapper(UserMapper.class);
        userService = new UserServiceImpl(userRepository, userMapper);
    }

    @Test
    void create_ShouldSaveAndReturnUser() {
        User input = new User("Alice", "secret");
        when(userRepository.save(any())).thenAnswer(invocation -> {
            User u = invocation.getArgument(0);
            // эмулируем заполнение id
            u.setId(1L);
            return u;
        });

        User saved = userService.create(input);

        assertNotNull(saved.getId());
        assertEquals("Alice", saved.getName());
        verify(userRepository).save(input);
    }

    @Test
    void getById_ShouldReturnDto_WhenExists() {
        User user = new User("Bob", "pwd");
        user.setId(2L);
        user.setEmails(Collections.emptySet());
        user.setPhones(Collections.emptySet());
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));

        UserDto dto = userService.getById(2L);

        assertEquals(2L, dto.getId());
        assertEquals("Bob", dto.getName());
    }

    @Test
    void getAll_ShouldReturnListOfDtos() {
        User u = new User("Carl", "pwd");
        u.setId(3L);
        u.setEmails(Collections.emptySet());
        u.setPhones(Collections.emptySet());
        when(userRepository.findAll()).thenReturn(Collections.singletonList(u));

        var list = userService.getAll();
        assertEquals(1, list.size());
        assertEquals("Carl", list.get(0).getName());
    }
}
