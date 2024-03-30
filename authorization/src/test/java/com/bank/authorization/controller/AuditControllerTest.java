package com.bank.authorization.controller;

import com.bank.authorization.dto.AuditDto;
import com.bank.authorization.service.AuditService;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(AuditController.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
@ExtendWith(MockitoExtension.class)
class AuditControllerTest {

    @MockBean
    private AuditService auditService;

    @Autowired
    MockMvc mockMvc;

    AuditDto getAuditDto() {
        return new AuditDto(
                10L,
                "entityType",
                "operationType",
                "createdBy",
                "modifiedBy",
                new Timestamp(10L),
                new Timestamp(20L),
                "newEntityJson",
                "entityJson"
        );
    }

    @Test
    @DisplayName("Чтение по id, позитивный сценарий")
    void readByIDPositiveTest() throws Exception {
        AuditDto expectedDto = getAuditDto();
        Long expectedId = expectedDto.getId();

        when(auditService.findById(expectedId)).thenReturn(expectedDto);

        MvcResult mvcResult = mockMvc.perform((get("/audit/" + expectedId)))
                .andExpect((status().isOk()))
                .andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();
        AuditDto actualDto = new ObjectMapper().readValue(responseContent, AuditDto.class);
        assertEquals(expectedDto, actualDto);
    }

    @Test
    @DisplayName("Чтение по id по некорректному запросу, негативный сценарий")
    void readByIdIncorrectRequest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/audit/badrequest"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(ex -> ex.getResolvedException()
                        .getClass()
                        .equals(MethodArgumentTypeMismatchException.class))
                .andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();
        assertEquals(responseContent, "Проверьте корректность применяемого запроса.");
    }
}