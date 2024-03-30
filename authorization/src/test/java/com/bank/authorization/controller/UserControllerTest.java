package com.bank.authorization.controller;

import com.bank.authorization.dto.UserDto;
import com.bank.authorization.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class UserControllerTest {

    @MockBean
    UserService userService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    UserDto getUserDto() {
        return new UserDto(
                10L,
                "User10",
                "123",
                1111L
        );
    }

    @Test
    @DisplayName("Создание User по id, позитивный сценарий")
    void createUserByIdPositiveTest() throws Exception {
        UserDto expectedDto = getUserDto();
        String UserDtoJson = objectMapper.writeValueAsString(expectedDto);

        when(userService.save(expectedDto)).thenReturn(expectedDto);

        MvcResult mvcResult = mockMvc.perform(post("/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(UserDtoJson))
                .andExpect(status().isCreated())
                .andReturn();
        String responseContent = mvcResult.getResponse().getContentAsString();

        UserDto actualDto = new ObjectMapper().readValue(responseContent, UserDto.class);

        assertEquals(expectedDto, actualDto);
    }

    @Test
    @DisplayName("Создание User с неверным запросом, негативный сценарий")
    void createUserByIdBadRequestNegativeTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/create/badrequest"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(MvcResult::getHandler)
                .andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        assertEquals(response, "");

    }

    @Test
    @DisplayName("Выдача User по id, позитивный сценарий")
    void readByIdPositiveTest() throws Exception {
        UserDto expectedDto = getUserDto();
        Long expectedUserId = expectedDto.getId();
        when(userService.findById(expectedUserId)).thenReturn(expectedDto);
        MvcResult mvcResult = mockMvc.perform(get("/read/" + expectedUserId))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();
        UserDto actualDto = new ObjectMapper().readValue(responseContent, UserDto.class);

        assertEquals(expectedDto, actualDto);
    }

    @Test
    @DisplayName("Выдача User по id, неверный запрос, негативный сценарий")
    void ReadUserByIdIncorrectRequestNegativeTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/read/badrequest"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(ex -> ex.getResolvedException()
                        .getClass()
                        .equals(MethodArgumentTypeMismatchException.class))
                .andReturn();
        String responseContent = mvcResult.getResponse().getContentAsString();

        assertEquals(responseContent, "Проверьте корректность применяемого запроса.");
    }

    @Test
    @DisplayName("Обновление User по id, позитивный сценарий")
    void updateUserByIdPositiveTest() throws Exception {
        UserDto expectedDto = getUserDto();
        Long expectedUserId = expectedDto.getId();
        String userDtoJson = objectMapper.writeValueAsString(expectedDto);
        when(userService.update(expectedUserId, expectedDto)).thenReturn(expectedDto);
        MvcResult mvcResult = mockMvc.perform(put("/" + expectedUserId + "/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoJson))
                .andExpect(status().isOk())
                .andReturn();
        String responseContent = mvcResult.getResponse().getContentAsString();

        UserDto actualDto = new ObjectMapper().readValue(responseContent, UserDto.class);

        assertEquals(expectedDto, actualDto);
    }

    @Test
    @DisplayName("Обновление User с неверным методом запросом, негативный сценарий")
    void updateUserByIdBadRequestNegativeTest() throws Exception {
        UserDto expectedDto = getUserDto();
        Long expectedUserId = expectedDto.getId();
        when(userService.update(expectedUserId, expectedDto)).thenReturn(expectedDto);
        MvcResult mvcResult = mockMvc.perform(get("/" + expectedUserId + "/update"))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed())
                .andExpect(ex -> ex.getResolvedException()
                        .getClass()
                        .equals(HttpRequestMethodNotSupportedException.class))
                .andReturn();
        String responseContent = mvcResult.getResponse().getContentAsString();

        assertEquals(responseContent, "Выбранный вами метод не поддерживается.");
    }


    @Test
    @DisplayName("Выдача User по списку id, позитивный сценарий")
    void readAllUsersByListPositiveTest() throws Exception {
        List<Long> ids = new ArrayList<>(Arrays.asList(1L, 2L, 3L));
        List<UserDto> expectedResult = getUserDtoList();
        String idsJson = objectMapper.writeValueAsString(ids);
        when(userService.findAllByIds(ids)).thenReturn(expectedResult);
        MvcResult result = mockMvc.perform(get("/read/all?ids=1,2,3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(idsJson))
                .andExpect(status().isOk())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();

        List<UserDto> actualResult = new ObjectMapper().readValue(responseContent, new TypeReference<>() {});

        assertEquals(expectedResult, actualResult);
    }

    List<UserDto> getUserDtoList() {
        Arrays Arrays;
        return new ArrayList<>(java.util.Arrays.asList(
                new UserDto(1L, "User1", "111", 1111L),
                new UserDto(2L, "User2", "111", 1112L),
                new UserDto(3L, "User3", "111", 1113L)
        ));
    }
}