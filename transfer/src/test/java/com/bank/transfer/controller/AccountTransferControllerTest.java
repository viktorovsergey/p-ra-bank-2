package com.bank.transfer.controller;

import com.bank.transfer.dto.AccountTransferDto;
import com.bank.transfer.service.AccountTransferService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AccountTransferController.class)
class AccountTransferControllerTest {
    private final AccountTransferDto accountTransferDto =
            new AccountTransferDto(1L, 1L,
                    BigDecimal.valueOf(1), "1", 1L);
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AccountTransferService accountTransferService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Чтение всех account по ids. Позитивный сценарий")
    void readAllPositiveTest() throws Exception {
        List<Long> ids = Arrays.asList(1L, 2L, 3L);
        List<AccountTransferDto> accountTransferDtoList = Arrays.asList(
                new AccountTransferDto(1L, 1L,
                        BigDecimal.valueOf(1), "1", 1L),
                new AccountTransferDto(2L, 2L,
                        BigDecimal.valueOf(2), "2", 2L),
                new AccountTransferDto(3L, 3L,
                        BigDecimal.valueOf(3), "3", 3L));

        Mockito.when(accountTransferService.findAllById(ids))
                .thenReturn(accountTransferDtoList);

        mockMvc.perform(get("/account/read/all").param("ids", "1", "2", "3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[2].id").value(3L))
                .andExpect(jsonPath("$[2].accountNumber").value(3L))
                .andExpect(jsonPath("$[2].amount").value(BigDecimal.valueOf(3)))
                .andExpect(jsonPath("$[2].purpose").value("3"))
                .andExpect(jsonPath("$[2].accountDetailsId").value(3L));
    }

    @Test
    @DisplayName("Чтение всех account по ids. Негативный сценарий")
    void readAllNegativeTest() throws Exception {
        List<Long> ids = Arrays.asList(1L, 2L, 3L);
        Mockito.when(accountTransferService.findAllById(ids))
                .thenThrow(new EntityNotFoundException());

        mockMvc.perform(get("/account/read/all").param("ids", "1", "2", "3"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Чтение account по id. Позитивный сценарий")
    void readPositiveTest() throws Exception {
        Long id = 1L;

        Mockito.when(accountTransferService.findById(id))
                .thenReturn(accountTransferDto);
        mockMvc.perform(get("/account/read/{id}", 1)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.accountNumber").value(1L))
                .andExpect(jsonPath("$.amount").value(BigDecimal.valueOf(1)))
                .andExpect(jsonPath("$.purpose").value("1"))
                .andExpect(jsonPath("$.accountDetailsId").value(1L));
    }

    @Test
    @DisplayName("Чтение account по id. Негативный сценарий")
    void readNegativeTest() throws Exception {
        Mockito.when(accountTransferService.findById(any()))
                .thenThrow(new EntityNotFoundException());
        mockMvc.perform(get("/account/read/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Создание account по id. Позитивный сценарий")
    void createPositiveTest() throws Exception {
        Mockito.when(accountTransferService.save(any(AccountTransferDto.class)))
                .thenAnswer(invocation -> {
                    AccountTransferDto createdDto = invocation.getArgument(0);
                    createdDto.setId(1L);
                    return createdDto;
                });

        mockMvc.perform(post("/account/create").content(
                                objectMapper.writeValueAsString(accountTransferDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.accountNumber").value(1L))
                .andExpect(jsonPath("$.amount").value(BigDecimal.valueOf(1)))
                .andExpect(jsonPath("$.purpose").value("1"))
                .andExpect(jsonPath("$.accountDetailsId").value(1L));
    }

    @Test
    @DisplayName("Создание account по id. Негативный сценарий")
    void createNegativeTest() throws Exception {
        Mockito.when(accountTransferService.save(any(AccountTransferDto.class)))
                .thenAnswer(invocation -> {
                    AccountTransferDto createdDto = invocation.getArgument(0);
                    createdDto.setId(1L);
                    return createdDto;
                });

        mockMvc.perform(post("/account/create").content(
                                objectMapper.writeValueAsString(null))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Обновление account. Позитивный сценарий")
    void updatePositiveTest() throws Exception {
        Long id = 1L;
        Mockito.when(accountTransferService.update(id, accountTransferDto))
                .thenReturn(accountTransferDto);

        mockMvc.perform(put("/account/update/{id}", 1).content(
                                objectMapper.writeValueAsString(accountTransferDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.accountNumber").value(1L))
                .andExpect(jsonPath("$.amount").value(BigDecimal.valueOf(1)))
                .andExpect(jsonPath("$.purpose").value("1"))
                .andExpect(jsonPath("$.accountDetailsId").value(1L));
    }

    @Test
    @DisplayName("Обновление account. Негативный сценарий")
    void updateNegativeTest() throws Exception {
        Long id = 1L;
        Mockito.when(accountTransferService.update(id, accountTransferDto))
                .thenReturn(accountTransferDto);

        mockMvc.perform(put("/account/update/{id}", 1).content(
                                objectMapper.writeValueAsString(null))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }
}