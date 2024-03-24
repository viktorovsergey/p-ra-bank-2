package com.bank.account.controller;

import com.bank.account.dto.AuditDto;
import com.bank.account.service.AccountAuditServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import javax.persistence.EntityNotFoundException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Тест класса AccountAuditController")
@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
@WebMvcTest(AccountAuditController.class)
public class AccountAuditControllerTest {
    final String PATH = "/audit";
    final AuditDto DTO = new AuditDto(1L, "account", "create", "user",
            "user", null, null,"abc","1234");

    EntityNotFoundException notFoundException = new EntityNotFoundException();
    @MockBean
    AccountAuditServiceImpl service;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    ResultActions performRequest(RequestBuilder requestBuilder) throws Exception {
        return mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(DTO.getId()));
    }

    ResultActions performRequestBadResult(RequestBuilder requestBuilder, ResultMatcher resultMatcher) throws Exception {
        return mockMvc.perform(requestBuilder)
                .andExpect(resultMatcher);
    }

    @Test
    @DisplayName("чтение по существующему id, позитивный тест")
    void readPositiveTest() throws Exception {
        String url = PATH + "/1";
        RequestBuilder requestBuilder = get(url);
        doReturn(DTO).when(service).findById(1L);
        performRequest(requestBuilder);
        verify(service).findById(any());
    }

    @Test
    @DisplayName("чтение по несуществующему id, негативный тест")
    void readNegativeTest() throws Exception {
        String url = PATH + "/1";
        RequestBuilder requestBuilder = get(url);
        doThrow(notFoundException).when(service).findById(1L);
        performRequestBadResult(requestBuilder, status().isNotFound());
        verify(service).findById(any());
    }
}