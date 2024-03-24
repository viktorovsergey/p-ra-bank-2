package com.bank.antifraud.controller;

import com.bank.antifraud.dto.AuditDto;
import com.bank.antifraud.service.AuditService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuditController.class)
class AuditControllerTest {
    @Autowired
    MockMvc mvc;
    @MockBean
    AuditService service;

    @Test
    @DisplayName("чтение по id, позитивный сценарий")
    void readByIdPositiveTest() throws Exception {
        AuditDto dto = new AuditDto(1L, "entityType1", "operationType1",
                "createdBy1", "modifiedBy1", new Timestamp(1L), new Timestamp(1L),
                "newEntityJson1", "entityJson1");

        when(service.findById(1L)).thenReturn(dto);

        mvc.perform(get("/audit/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.entityType").value("entityType1"))
                .andExpect(jsonPath("$.operationType").value("operationType1"))
                .andExpect(jsonPath("$.createdBy").value("createdBy1"))
                .andExpect(jsonPath("$.modifiedBy").value("modifiedBy1"))
                .andExpect(jsonPath("$.newEntityJson").value("newEntityJson1"))
                .andExpect(jsonPath("$.entityJson").value("entityJson1"));
    }
    @Test
    @DisplayName("чтение по несуществующему id, негативный сценарий")
    void readByNonExistIdNegativeTest() throws Exception {
        when(service.findById(anyLong())).thenThrow(new EntityNotFoundException());

        mvc.perform(get("/audit/1")
                        .contentType(TEXT_PLAIN))
                .andExpect(status().isNotFound());
    }
}