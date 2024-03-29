package com.bank.profile.controller;

import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.service.RegistrationService;
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
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RegistrationController.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
@ExtendWith(MockitoExtension.class)
@DisplayName("Тест класса RegistrationController")
class RegistrationControllerTest {
    final RegistrationDto DTO = new RegistrationDto(1L, "Russia", "Altaysky kray",
            "Barnaul", null, null, "prospect Lenina", "56",
            null, null, 656000L);
    final String URL = "/registration";

    final List<RegistrationDto> LIST_DTO = List.of(DTO);

    @MockBean
    RegistrationService registrationService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("чтение по существующему id, позитивный тест")
    void readPositiveTest() throws Exception {

        RequestBuilder requestBuilder = get(URL + "/read/1");
        doReturn(DTO).when(registrationService).findById(1L);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(DTO.getId()));
        verify(registrationService).findById(any());
    }

    @Test
    @DisplayName("чтение по несуществующему id, негативный тест")
    void readNegativeTest() throws Exception {
        RequestBuilder requestBuilder = get(URL + "/read/1");
        doThrow(new EntityNotFoundException()).when(registrationService).findById(1L);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound());
        verify(registrationService).findById(any());
    }

    @Test
    @DisplayName("создание пользователя, на вход подан dto, позитивный тест")
    void createPositiveTest() throws Exception {
        RequestBuilder requestBuilder = post(URL + "/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(DTO));
        doReturn(DTO).when(registrationService).save(DTO);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(DTO.getId()));
        verify(registrationService).save(any());
    }

    @Test
    @DisplayName("создание пользователя, на вход подан dto, негативный тест")
    void createNegativeTest() throws Exception {
        RequestBuilder requestBuilder = post(URL + "/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(DTO));
        doThrow(new NullPointerException()).when(registrationService).save(DTO);
        mockMvc.perform(requestBuilder)
                .andExpect(status().is5xxServerError());
        verify(registrationService).save(any());
    }

    @Test
    @DisplayName("обновление пользователя по существующему id, позитивный тест")
    void readUpdatePositiveTest() throws Exception {
        RequestBuilder requestBuilder = put(URL + "/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(DTO));
        doReturn(DTO).when(registrationService).update(1L, DTO);
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(DTO.getId()));
        verify(registrationService).update(any(), any());
    }

    @Test
    @DisplayName("обновление пользователя по несуществующему id, негативный тест")
    void readUpdateNegativeTest() throws Exception {
        RequestBuilder requestBuilder = put(URL + "/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(DTO));
        doThrow(new NullPointerException()).when(registrationService).update(1L, DTO);
        mockMvc.perform(requestBuilder)
                .andExpect(status().is5xxServerError());
        verify(registrationService).update(any(), any());
    }

    @Test
    @DisplayName("чтение всех пользователей по существующему ids, позитивный тест")
    void readAllPositiveTest() throws Exception {
        RequestBuilder requestBuilder = get(URL + "/read/all")
                .param("ids","1").contentType(MediaType.APPLICATION_JSON);
        doReturn(LIST_DTO).when(registrationService).findAllById(List.of(1L));
        mockMvc.perform(requestBuilder).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(DTO.getId()));
        verify(registrationService).findAllById(any());
    }

    @Test
    @DisplayName("чтение всех пользователей по несуществующему ids, негативный тест")
    void readAllNegativeTest() throws Exception {
        RequestBuilder requestBuilder = get(URL + "/read/all")
                .param("ids","1").contentType(MediaType.APPLICATION_JSON);
        doThrow(new NullPointerException()).when(registrationService).findAllById(List.of(1L));
        mockMvc.perform(requestBuilder)
                .andExpect(status().is5xxServerError());
        verify(registrationService).findAllById(any());
    }
}