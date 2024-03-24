package com.bank.antifraud.controller;

import com.bank.antifraud.dto.SuspiciousCardTransferDto;
import com.bank.antifraud.service.SuspiciousCardTransferService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SuspiciousCardTransferController.class)
class SuspiciousCardTransferControllerTest {
    @Autowired
    MockMvc mvc;
    @MockBean
    SuspiciousCardTransferService service;

    @Test
    @DisplayName("чтение по id, позитивный сценарий")
    void readByIdPositiveTest() throws Exception {
        SuspiciousCardTransferDto dto = new SuspiciousCardTransferDto(1L, 1L, true,
                true, "blockedReason", "suspiciousReason");

        when(service.findById(1L)).thenReturn(dto);

        mvc.perform(get("/suspicious/card/transfer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.cardTransferId").value(1L))
                .andExpect(jsonPath("$.isBlocked").value(true))
                .andExpect(jsonPath("$.isSuspicious").value(true))
                .andExpect(jsonPath("$.blockedReason").value("blockedReason"))
                .andExpect(jsonPath("$.suspiciousReason").value("suspiciousReason"));
    }

    @Test
    @DisplayName("чтение по несуществующему id, негативный сценарий")
    void readByNonExistIdNegativeTest() throws Exception {
        when(service.findById(anyLong())).thenThrow(new EntityNotFoundException("SuspiciousCardTransfer по данному id не существует"));

        mvc.perform(get("/suspicious/card/transfer/1")
                        .contentType(TEXT_PLAIN))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("чтение коллекции с корректными id, позитивный сценарий")
    void readAllByIdIfEntitiesAreExistPositiveTest() throws Exception {
        List<SuspiciousCardTransferDto> dtoList = List.of(new SuspiciousCardTransferDto(1L, 1L, true,
                        true, "blockedReason1", "suspiciousReason1"),
                new SuspiciousCardTransferDto(2L, 2L, true,
                        true, "blockedReason2", "suspiciousReason2"));
        List<Long> requestParamList = List.of(1L, 2L);

        when(service.findAllById(requestParamList)).thenReturn(dtoList);

        mvc.perform(get("/suspicious/card/transfer?ids=1&ids=2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @DisplayName("чтение коллекции с некорректными id, негативный сценарий")
    void readALLByIdNonExistNegativeTest() throws Exception {
        when(service.findAllById(anyList())).thenThrow(new EntityNotFoundException("SuspiciousCardTransfer по данному id не существует"));

        mvc.perform(get("/suspicious/card/transfer?ids=1&ids=2")
                        .contentType(TEXT_PLAIN))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("создание дто с корректными данными, позитивный сценарий")
    void createPositiveTest() throws Exception {
        SuspiciousCardTransferDto expectedDto = new SuspiciousCardTransferDto(1L, 1L, true,
                true, "blockedReason", "suspiciousReason");
        when(service.save(expectedDto)).thenReturn(expectedDto);

        mvc.perform(post("/suspicious/card/transfer/create")
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(expectedDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.cardTransferId").value(1L))
                .andExpect(jsonPath("$.isBlocked").value(true))
                .andExpect(jsonPath("$.isSuspicious").value(true))
                .andExpect(jsonPath("$.blockedReason").value("blockedReason"))
                .andExpect(jsonPath("$.suspiciousReason").value("suspiciousReason"));
    }

    @Test
    @DisplayName("создание дто с некорректными данными, негативный сценарий")
    void createNegativeTest() throws Exception {
        SuspiciousCardTransferDto actualDto = new SuspiciousCardTransferDto(1L, 1L, true,
                true, "blockedReason", "suspiciousReason");
        when(service.save(actualDto)).thenThrow(new NullPointerException());

        mvc.perform(post("/suspicious/card/transfer/create")
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(actualDto)))
                .andExpect(status().is5xxServerError());
    }

    @Test
    @DisplayName("обновление дто с корректным id, позитивный сценарий")
    void updateIfEntityIsExistPositiveTest() throws Exception {
        SuspiciousCardTransferDto expectedDto = new SuspiciousCardTransferDto(1L, 1L, false,
                false, "unblockedReason", "unsuspectingReason");

        when(service.update(1L, expectedDto)).thenReturn(expectedDto);

        mvc.perform(put("/suspicious/card/transfer/1")
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(expectedDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.cardTransferId").value(1L))
                .andExpect(jsonPath("$.isBlocked").value(false))
                .andExpect(jsonPath("$.isSuspicious").value(false))
                .andExpect(jsonPath("$.blockedReason").value("unblockedReason"))
                .andExpect(jsonPath("$.suspiciousReason").value("unsuspectingReason"));
    }

    @Test
    @DisplayName("обновление дто с некорректным id, негативный сценарий")
    void updateNonExistEntityNegativeTest() throws Exception {
        SuspiciousCardTransferDto expectedDto = new SuspiciousCardTransferDto(1L, 1L, false,
                false, "unblockedReason", "unsuspectingReason");

        when(service.update(1L, expectedDto)).thenThrow(new EntityNotFoundException("SuspiciousCardTransfer по данному id не существует"));

        mvc.perform(put("/suspicious/card/transfer/1")
                        .contentType(APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(expectedDto)))
                .andExpect(status().isNotFound());
    }
}