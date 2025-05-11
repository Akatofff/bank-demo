package com.example.bankdemotest.controller;

import com.example.bankdemo.controller.AccountController;
import com.example.bankdemo.dto.AccountDto;
import com.example.bankdemo.entity.Account;
import com.example.bankdemo.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired MockMvc mvc;
    @MockitoBean AccountService accountService;
    @Autowired ObjectMapper mapper;

    @Test
    void create_ShouldReturnAccount() throws Exception {
        Account acc = new Account();
        acc.setId(20L);
        acc.setBalance(BigDecimal.valueOf(300));
        when(accountService.create(any())).thenReturn(acc);

        mvc.perform(post("/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(acc)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(20))
                .andExpect(jsonPath("$.balance").value(300));
    }

    @Test
    void getByUser_ShouldReturnDtos() throws Exception {
        AccountDto dto = new AccountDto();
        dto.setId(20L);
        dto.setBalance(BigDecimal.valueOf(300));
        when(accountService.getByUser(5L)).thenReturn(List.of(dto));

        mvc.perform(get("/accounts/user/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(20));
    }

    @Test
    void transfer_ShouldInvokeService() throws Exception {
        doNothing().when(accountService).transfer(anyLong(), anyLong(), anyDouble());

        mvc.perform(post("/accounts/transfer")
                        .param("from", "1")
                        .param("to", "2")
                        .param("amount", "50"))
                .andExpect(status().isOk());
    }
}
