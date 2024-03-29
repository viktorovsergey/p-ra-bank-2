package com.bank.history.controller;

import com.bank.history.dto.HistoryDto;
import com.bank.history.service.HistoryService;

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

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(HistoryController.class)
@DisplayName("Тест класса HistoryController")
class HistoryControllerTest {
    final HistoryDto DTO = new HistoryDto(1L, 2L, 3L, 4L, 5L, 6L, 7L);
    private final List<HistoryDto> LIST_DTO = List.of(DTO);
    private String URL = "/api/history";

    @MockBean
    HistoryService historyService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("чтение по существующему id, позитивный тест")
    void readPositiveTest() throws Exception {
        RequestBuilder requestBuilder = get(URL + "/1");
        doReturn(DTO).when(historyService).readById(1L);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(DTO.getId()));
        verify(historyService).readById(any());
    }

    @Test
    @DisplayName("чтение по несуществующему id, негативный тест")
    void readNegativeTest() throws Exception {
        RequestBuilder requestBuilder = get(URL + "/1");
        doThrow(new EntityNotFoundException()).when(historyService).readById(1L);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound());
        verify(historyService).readById(any());
    }


    @Test
    @DisplayName("чтение всех пользователей по существующему id, позитивный тест")
    void readAllPositiveTest() throws Exception {
        RequestBuilder requestBuilder = get(URL)
                .param("id", "1").contentType(MediaType.APPLICATION_JSON);
        doReturn(LIST_DTO).when(historyService).readAllById(List.of(1L));

        mockMvc.perform(requestBuilder).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(DTO.getId()));
        verify(historyService).readAllById(any());
    }

    @Test
    @DisplayName("чтение всех пользователей по несуществующему ids, негативный тест")
    void readAllNegativeTest() throws Exception {
        RequestBuilder requestBuilder = get(URL + "/read/all")
                .param("ids", "1").contentType(MediaType.APPLICATION_JSON);
        doThrow(new NullPointerException()).when(historyService).readAllById(anyList());

        mockMvc.perform(requestBuilder)
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("создание пользователя, на вход подан dto, позитивный тест")
    void createPositiveTest() throws Exception {
        RequestBuilder requestBuilder = post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(DTO));
        when((historyService).create(any(HistoryDto.class))).thenReturn(DTO);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(DTO.getId())).andReturn().getResponse();
        verify(historyService).create(any());
    }

    @Test
    @DisplayName("создание пользователя, на вход подан dto, негативный тест")
    void createNegativeTest() throws Exception {
        RequestBuilder requestBuilder = post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(DTO));
        doThrow(new NullPointerException()).when(historyService).create(any(HistoryDto.class));

        mockMvc.perform(requestBuilder)
                .andExpect(status().is5xxServerError());
        verify(historyService).create(any());
    }

    @Test
    @DisplayName("обновление пользователя по существующему id, позитивный тест")
    void readUpdatePositiveTest() throws Exception {
        RequestBuilder requestBuilder = put(URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(DTO));
        when((historyService).update(any(), any(HistoryDto.class))).thenReturn(DTO);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(DTO.getId()));
        verify(historyService).update(any(), any());
    }


    @Test
    @DisplayName("обновление пользователя по несуществующему id, негативный тест")
    void readUpdateNegativeTest() throws Exception {
        RequestBuilder requestBuilder = put(URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(DTO));
        doThrow(new NullPointerException()).when(historyService).update(any(), any(HistoryDto.class));

        mockMvc.perform(requestBuilder)
                .andExpect(status().is5xxServerError());
        verify(historyService).update(any(), any());
    }
}