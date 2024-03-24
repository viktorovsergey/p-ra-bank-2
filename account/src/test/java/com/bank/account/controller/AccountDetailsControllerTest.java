package com.bank.account.controller;

import com.bank.account.dto.AccountDetailsDto;
import com.bank.account.service.AccountDetailsServiceImpl;
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
import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Тест класса AccountDetailsController")
@WebMvcTest(AccountDetailsController.class)
@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountDetailsControllerTest {

    final String PATH = "/details";
    final AccountDetailsDto DTO = new AccountDetailsDto(1L, 2L, 3L,
            4L, new BigDecimal(4), false, 7L);
    final List<AccountDetailsDto> DTO_LIST = List.of(DTO);

    final EntityNotFoundException notFoundException = new EntityNotFoundException();
    final NullPointerException nullPointerException = new NullPointerException();

    @MockBean
    AccountDetailsServiceImpl service;
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

    @Test
    @DisplayName("создание с передачей существующего ДТО, позитивный тест")
    void createPositiveTest() throws Exception {
        String url = PATH + "/create";
        RequestBuilder requestBuilder = post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(DTO));
        doReturn(DTO).when(service).save(DTO);
        performRequest(requestBuilder);
        verify(service).save(any());
    }

    @Test
    @DisplayName("создание с передачей несуществующего ДТО, негативный тест")
    void createNegativeTest() throws Exception {
        String url = PATH + "/create";
        RequestBuilder requestBuilder = post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(DTO));
        doThrow(nullPointerException).when(service).save(DTO);
        performRequestBadResult(requestBuilder, status().is5xxServerError());
        verify(service).save(any());
    }

    @Test
    @DisplayName("обновление пользователя по существующему id, позитивный тест")
    void readUpdatePositiveTest() throws Exception {
        String url = PATH + "/update/1";
        RequestBuilder requestBuilder = put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(DTO));
        doReturn(DTO).when(service).update(1L, DTO);
        performRequest(requestBuilder);
        verify(service).update(any(), any());
    }

    @Test
    @DisplayName("обновление пользователя по несуществующему id, негативный тест")
    void readUpdateNegativeTest() throws Exception {
        String url = PATH + "/update/1";
        RequestBuilder requestBuilder = put(url)
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(DTO));
        doThrow(nullPointerException).when(service).update(1L, DTO);
        performRequestBadResult(requestBuilder, status().is5xxServerError());
        verify(service).update(any(), any());
    }

    @Test
    @DisplayName("чтение всех пользователей по существующему ids, позитивный тест")
    void readAllPositiveTest() throws Exception {
        String url = PATH + "/read/all";
        RequestBuilder requestBuilder = get(url).param("ids","1").contentType(MediaType.APPLICATION_JSON);
        doReturn(DTO_LIST).when(service).findAllById(List.of(1L));
        mockMvc.perform(requestBuilder).andExpect(status().isOk()).andExpect(jsonPath("$[0].id").value(DTO.getId()));
        verify(service).findAllById(any());
    }

    @Test
    @DisplayName("чтение всех пользователей по несуществующему ids, негативный тест")
    void readAllNegativeTest() throws Exception {
        String url = PATH + "/read/all";
        RequestBuilder requestBuilder = get(url).param("ids","1").contentType(MediaType.APPLICATION_JSON);
        doThrow(nullPointerException).when(service).findAllById(List.of(1L));
        performRequestBadResult(requestBuilder, status().is5xxServerError());
        verify(service).findAllById(any());
    }
}
