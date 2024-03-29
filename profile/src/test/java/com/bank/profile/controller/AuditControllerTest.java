package com.bank.profile.controller;

import com.bank.profile.dto.AuditDto;
import com.bank.profile.service.impl.AuditServiceImpl;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuditController.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
@ExtendWith(MockitoExtension.class)
@DisplayName("Тест класса AuditController")
class AuditControllerTest {

    @MockBean
    AuditServiceImpl auditService;

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("чтение по существующему id, позитивный тест")
    void readPositiveTest() throws Exception {
        AuditDto DTO = new AuditDto(1L, "entityType", "operationType",
                "createdBy", "modifiedBy", new Timestamp(22L), new Timestamp(11L),
                "newEntityJson", "entityJson");

        RequestBuilder requestBuilder = get("/audit/1");
        doReturn(DTO).when(auditService).findById(1L);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(DTO.getId()));
        verify(auditService).findById(any());
    }

    @Test
    @DisplayName("чтение по несуществующему id, негативный тест")
    void readNegativeTest() throws Exception {
        RequestBuilder requestBuilder = get("/audit/1");
        doThrow(new EntityNotFoundException()).when(auditService).findById(1L);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound());
        verify(auditService).findById(any());
    }
}