package com.bank.transfer.controller;

import com.bank.transfer.dto.AuditDto;
import com.bank.transfer.service.AuditService;
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
import java.sql.Timestamp;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AuditController.class)
class AuditControllerTest {
    private final Timestamp timestamp =
            new Timestamp(System.currentTimeMillis());
    private final Timestamp timestamp1 =
            new Timestamp(System.currentTimeMillis() + 10000);
    @Autowired
    MockMvc mockMvc;
    @MockBean
    AuditService auditService;

    @Test
    @DisplayName("Чтение audit по id. Позитивный сценарий")
    void readPositiveTest() throws Exception {
        Long auditId = 1L;
        AuditDto auditDto =
                new AuditDto(1L, "1", "1",
                        "1", "2", timestamp, timestamp1,
                        "{newEntityJson}", "{entityJson}");

        Mockito.when(auditService.findById(auditId)).thenReturn(auditDto);

        mockMvc.perform(get("/audit/{id}", auditId))
                .andExpectAll(status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.id").value(1L),
                        jsonPath("$.entityType").value("1"),
                        jsonPath("$.operationType").value("1"),
                        jsonPath("$.createdBy").value("1"),
                        jsonPath("$.modifiedBy").value("2"),
                        jsonPath("$.newEntityJson").value("{newEntityJson}"),
                        jsonPath("$.entityJson").value("{entityJson}"));
    }

    @Test
    @DisplayName("Чтение audit по id. Негативный сценарий")
    void readNegativeTest() throws Exception {
        Mockito.when(auditService.findById(any()))
                .thenThrow(new EntityNotFoundException());
        mockMvc.perform(get("/audit/read/1")).andExpect(status().isNotFound());
    }
}