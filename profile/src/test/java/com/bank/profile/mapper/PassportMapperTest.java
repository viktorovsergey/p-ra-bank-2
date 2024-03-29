package com.bank.profile.mapper;

import com.bank.profile.dto.PassportDto;
import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.entity.PassportEntity;
import com.bank.profile.entity.RegistrationEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ExtendWith(MockitoExtension.class)
@DisplayName("Тест класса PassportMapper")
class PassportMapperTest {

    final PassportEntity ENTITY = new PassportEntity(1L, 1122, 334455L, "lastName",
            "firstName", "middleName", "M", LocalDate.ofEpochDay(2010 - 1 - 1),
            "birthPlace", "issuedBy", LocalDate.ofEpochDay(2010 - 1 - 1),
            12345678, LocalDate.ofEpochDay(2010 - 1 - 1), new RegistrationEntity());

    final PassportDto DTO = new PassportDto(1L, 1122, 334455L, "lastName",
            "firstName", "middleName", "M", LocalDate.ofEpochDay(2010 - 1 - 1),
            "birthPlace", "issuedBy", LocalDate.ofEpochDay(2010 - 1 - 1),
            12345678, LocalDate.ofEpochDay(2010 - 1 - 1), new RegistrationDto());

    final PassportMapper MAPPER = Mappers.getMapper(PassportMapper.class);

    @Test
    @DisplayName("маппинг в entity, на вход подан dto, позитивный тест")
    void toEntityPositiveTest() {
        PassportEntity result = MAPPER.toEntity(DTO);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(DTO.getSeries(), result.getSeries()),
                () -> assertEquals(DTO.getNumber(), result.getNumber()),
                () -> assertEquals(DTO.getLastName(), result.getLastName()),
                () -> assertEquals(DTO.getFirstName(), result.getFirstName()),
                () -> assertEquals(DTO.getMiddleName(), result.getMiddleName()),
                () -> assertEquals(DTO.getGender(), result.getGender()),
                () -> assertEquals(DTO.getBirthDate(), result.getBirthDate()),
                () -> assertEquals(DTO.getBirthPlace(), result.getBirthPlace()),
                () -> assertEquals(DTO.getIssuedBy(), result.getIssuedBy()),
                () -> assertEquals(DTO.getDateOfIssue(), result.getDateOfIssue()),
                () -> assertEquals(DTO.getDivisionCode(), result.getDivisionCode()),
                () -> assertEquals(DTO.getDateOfIssue(), result.getDateOfIssue())
        );
    }

    @Test
    @DisplayName("маппинг в entity, на вход подан null, негативный тест")
    void toEntityNegativeTest() {
        PassportEntity result = MAPPER.toEntity(null);

        assertNull(result);
    }

    @Test
    @DisplayName("маппинг в dto, на вход подан entity, позитивный тест")
    void toDtoPositiveTest() {
        PassportDto result = MAPPER.toDto(ENTITY);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(ENTITY.getSeries(), result.getSeries()),
                () -> assertEquals(ENTITY.getNumber(), result.getNumber()),
                () -> assertEquals(ENTITY.getLastName(), result.getLastName()),
                () -> assertEquals(ENTITY.getFirstName(), result.getFirstName()),
                () -> assertEquals(ENTITY.getMiddleName(), result.getMiddleName()),
                () -> assertEquals(ENTITY.getGender(), result.getGender()),
                () -> assertEquals(ENTITY.getBirthDate(), result.getBirthDate()),
                () -> assertEquals(ENTITY.getBirthPlace(), result.getBirthPlace()),
                () -> assertEquals(ENTITY.getIssuedBy(), result.getIssuedBy()),
                () -> assertEquals(ENTITY.getDateOfIssue(), result.getDateOfIssue()),
                () -> assertEquals(ENTITY.getDivisionCode(), result.getDivisionCode()),
                () -> assertEquals(ENTITY.getDateOfIssue(), result.getDateOfIssue())
        );
    }

    @Test
    @DisplayName("маппинг в dto, на вход подан null, негативный тест")
    void toDtoNegativeTest() {
        PassportDto result = MAPPER.toDto(null);
        assertNull(result);
    }

    @Test
    @DisplayName("слияние в entity, на вход подан dto и entity, позитивный тест")
    void mergeToEntityPositiveTest() {
        PassportEntity result = MAPPER.mergeToEntity(DTO, ENTITY);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(ENTITY.getSeries(), result.getSeries()),
                () -> assertEquals(ENTITY.getNumber(), result.getNumber()),
                () -> assertEquals(ENTITY.getLastName(), result.getLastName()),
                () -> assertEquals(ENTITY.getFirstName(), result.getFirstName()),
                () -> assertEquals(ENTITY.getMiddleName(), result.getMiddleName()),
                () -> assertEquals(ENTITY.getGender(), result.getGender()),
                () -> assertEquals(ENTITY.getBirthDate(), result.getBirthDate()),
                () -> assertEquals(ENTITY.getBirthPlace(), result.getBirthPlace()),
                () -> assertEquals(ENTITY.getIssuedBy(), result.getIssuedBy()),
                () -> assertEquals(ENTITY.getDateOfIssue(), result.getDateOfIssue()),
                () -> assertEquals(ENTITY.getDivisionCode(), result.getDivisionCode()),
                () -> assertEquals(ENTITY.getDateOfIssue(), result.getDateOfIssue())
        );
    }

    @Test
    @DisplayName("слияние в entity, на вход подан null, негативный тест")
    void mergeToEntityNegativeTest() {
        PassportEntity result = MAPPER.mergeToEntity(null, null);

        assertNull(result);
    }

    @Test
    @DisplayName("маппинг в List<dto>, на вход подан List<entity>, негативный тест")
    void toDtoListPositiveTest() {
        List<PassportEntity> entityList = new ArrayList<>();
        entityList.add(ENTITY);

        List<PassportDto> result = MAPPER.toDtoList(entityList);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(ENTITY.getSeries(), result.get(0).getSeries()),
                () -> assertEquals(ENTITY.getNumber(), result.get(0).getNumber()),
                () -> assertEquals(ENTITY.getLastName(), result.get(0).getLastName()),
                () -> assertEquals(ENTITY.getFirstName(), result.get(0).getFirstName()),
                () -> assertEquals(ENTITY.getMiddleName(), result.get(0).getMiddleName()),
                () -> assertEquals(ENTITY.getGender(), result.get(0).getGender()),
                () -> assertEquals(ENTITY.getBirthDate(), result.get(0).getBirthDate()),
                () -> assertEquals(ENTITY.getBirthPlace(), result.get(0).getBirthPlace()),
                () -> assertEquals(ENTITY.getIssuedBy(), result.get(0).getIssuedBy()),
                () -> assertEquals(ENTITY.getDateOfIssue(), result.get(0).getDateOfIssue()),
                () -> assertEquals(ENTITY.getDivisionCode(), result.get(0).getDivisionCode()),
                () -> assertEquals(ENTITY.getDateOfIssue(), result.get(0).getDateOfIssue())
        );
    }

    @Test
    @DisplayName("маппинг в List<dto>, на вход подан null, негативный тест")
    void toDtoListNegativeTest() {
        List<PassportDto> result = MAPPER.toDtoList(null);

        assertNull(result);
    }

}