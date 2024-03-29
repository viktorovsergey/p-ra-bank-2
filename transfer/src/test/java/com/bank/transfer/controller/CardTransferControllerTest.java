package com.bank.transfer.controller;

import com.bank.transfer.dto.AccountTransferDto;
import com.bank.transfer.dto.CardTransferDto;
import com.bank.transfer.service.CardTransferService;
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
@WebMvcTest(CardTransferController.class)
class CardTransferControllerTest {
    static final CardTransferDto cardTransferDto =
            new CardTransferDto(1L, 1111_2222_3333_4444L, BigDecimal.valueOf(1),
                    "1", 1L);
    @Autowired
    MockMvc mockMvc;
    @MockBean
    CardTransferService cardTransferService;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("Чтение всех cardTransfer по ids. Позитивный сценарий")
    void readAllPositiveTest() throws Exception {
        List<Long> ids = Arrays.asList(1L, 2L, 3L);
        List<CardTransferDto> cardTransferDtoList = Arrays.asList(
                new CardTransferDto(1L, 1111_1111_1111_1111L,
                        BigDecimal.valueOf(1), "1", 1L),
                new CardTransferDto(2L, 2222_2222_2222_2222L,
                        BigDecimal.valueOf(2), "2", 2L),
                new CardTransferDto(3L, 3333_3333_3333_3333L,
                        BigDecimal.valueOf(3), "3", 3L));

        Mockito.when(cardTransferService.findAllById(ids))
                .thenReturn(cardTransferDtoList);

        mockMvc.perform(get("/card/read/all").param("ids", "1", "2", "3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(
                        jsonPath("$[0].cardNumber").value(1111_1111_1111_1111L))
                .andExpect(jsonPath("$[0].amount").value(BigDecimal.valueOf(1)))
                .andExpect(jsonPath("$[0].purpose").value("1"))
                .andExpect(jsonPath("$[0].accountDetailsId").value(1L))

                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(
                        jsonPath("$[1].cardNumber").value(2222_2222_2222_2222L))
                .andExpect(jsonPath("$[1].amount").value(BigDecimal.valueOf(2)))
                .andExpect(jsonPath("$[1].purpose").value("2"))
                .andExpect(jsonPath("$[1].accountDetailsId").value(2L))

                .andExpect(jsonPath("$[2].id").value(3L))
                .andExpect(
                        jsonPath("$[2].cardNumber").value(3333_3333_3333_3333L))
                .andExpect(jsonPath("$[2].amount").value(BigDecimal.valueOf(3)))
                .andExpect(jsonPath("$[2].purpose").value("3"))
                .andExpect(jsonPath("$[2].accountDetailsId").value(3L));
    }

    @Test
    @DisplayName("Чтение всех cardTransfer по ids. Негативный сценарий")
    void readAllNegativeTest() throws Exception {
        List<Long> ids = Arrays.asList(1L, 2L, 3L);
        Mockito.when(cardTransferService.findAllById(ids))
                .thenThrow(new EntityNotFoundException());

        mockMvc.perform(get("/card/read/all").param("ids", "1", "2", "3"))
                .andExpect(status().isNotFound());
    }


    @Test
    @DisplayName("Чтение cardTransfer по id. Позитивный сценарий")
    void readPositiveTest() throws Exception {
        Long id = 1L;
        CardTransferDto cardTransferDto =
                new CardTransferDto(1L, 1111_2222_3333_4444L,
                        BigDecimal.valueOf(1), "1", 2L);

        Mockito.when(cardTransferService.findById(id))
                .thenReturn(cardTransferDto);

        mockMvc.perform(get("/card/read/{id}", 1)).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.cardNumber").value(1111_2222_3333_4444L))
                .andExpect(jsonPath("$.amount").value(BigDecimal.valueOf(1)))
                .andExpect(jsonPath("$.purpose").value("1"))
                .andExpect(jsonPath("$.accountDetailsId").value(2L));
    }

    @Test
    @DisplayName("Чтение cardTransfer по id. Негативный сценарий")
    void readNegativeTest() throws Exception {
        Mockito.when(cardTransferService.findById(any()))
                .thenThrow(new EntityNotFoundException());
        mockMvc.perform(get("/card/read/1")).andExpect(status().isNotFound());
    }


    @Test
    @DisplayName("Создание cardTransfer. Позитивный сценарий")
    void createPositiveTest() throws Exception {
        CardTransferDto cardTransferDto =
                new CardTransferDto(null, 1111_2222_3333_4444L,
                        BigDecimal.valueOf(123456789),
                        "target", 2L);

        Mockito.when(cardTransferService.save(any(CardTransferDto.class)))
                .thenAnswer(invocation -> {
                    CardTransferDto createdDto = invocation.getArgument(0);
                    createdDto.setId(1L);
                    return createdDto;
                });

        mockMvc.perform(
                        post("/card/create").content(
                                        objectMapper.writeValueAsString(cardTransferDto))
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.cardNumber").value(1111_2222_3333_4444L))
                .andExpect(jsonPath("$.amount").value(
                        BigDecimal.valueOf(123456789)))
                .andExpect(jsonPath("$.purpose").value("target"))
                .andExpect(jsonPath("$.accountDetailsId").value(2L));
    }

    @Test
    @DisplayName("Создание cardTransfer. Негативный сценарий")
    void createNegativeTest() throws Exception {
        Mockito.when(cardTransferService.save(any(CardTransferDto.class)))
                .thenAnswer(invocation -> {
                    AccountTransferDto createdDto = invocation.getArgument(0);
                    createdDto.setId(1L);
                    return createdDto;
                });

        mockMvc.perform(post("/card/create").content(
                                objectMapper.writeValueAsString(null))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Обновление cardTransfer. Позитивный сценарий")
    void updatePositiveTest() throws Exception {
        Long id = 1L;

        Mockito.when(cardTransferService.update(id, cardTransferDto))
                .thenReturn(cardTransferDto);

        mockMvc.perform(put("/card/update/{id}", 1).content(
                                objectMapper.writeValueAsString(cardTransferDto))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.id").value(1L),
                        jsonPath("$.cardNumber").value(1111_2222_3333_4444L),
                        jsonPath("$.amount").value(BigDecimal.valueOf(1)),
                        jsonPath("$.purpose").value("1"),
                        jsonPath("$.accountDetailsId").value(1L));
    }


    @Test
    @DisplayName("Обновление cardTransfer. Негативный сценарий")
    void updateNegativeTest() throws Exception {
        Long id = 1L;
        Mockito.when(cardTransferService.update(id, cardTransferDto))
                .thenReturn(cardTransferDto);

        mockMvc.perform(put("/card/update/{id}", 1).content(
                                objectMapper.writeValueAsString(null))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest());
    }
}
