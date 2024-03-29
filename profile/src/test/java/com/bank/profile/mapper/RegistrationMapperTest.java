package com.bank.profile.mapper;

import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.entity.RegistrationEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ExtendWith(MockitoExtension.class)
@DisplayName("Тест класса RegistrationMapper")
class RegistrationMapperTest {
    final RegistrationEntity ENTITY = new RegistrationEntity(1L, "Russia", "Altaysky kray",
            "Barnaul", null, null, "prospect Lenina", "56",
            null, null, 656000L);

    final RegistrationDto DTO = new RegistrationDto(1L, "Russia", "Altaysky kray",
            "Barnaul", null, null, "prospect Lenina", "56",
            null, null, 656000L);

    final RegistrationMapper MAPPER = Mappers.getMapper(RegistrationMapper.class);

    @Test
    @DisplayName("маппинг в entity, на вход подан dto, позитивный тест")
    void toEntityPositiveTest() {
        RegistrationEntity result = MAPPER.toEntity(DTO);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(ENTITY.getCountry(), result.getCountry()),
                () -> assertEquals(ENTITY.getRegion(), result.getRegion()),
                () -> assertEquals(ENTITY.getCity(), result.getCity()),
                () -> assertEquals(ENTITY.getStreet(), result.getStreet()),
                () -> assertEquals(ENTITY.getHouseNumber(), result.getHouseNumber()),
                () -> assertEquals(ENTITY.getIndex(), result.getIndex())
        );

    }

    @Test
    @DisplayName("маппинг в entity, на вход подан null, негативный тест")
    void toEntityNegativeTest() {
        RegistrationEntity result = MAPPER.toEntity(null);

        assertNull(result);
    }

    @Test
    @DisplayName("маппинг в dto, на вход подан entity, позитивный тест")
    void toDtoPositiveTest() {
        RegistrationDto result = MAPPER.toDto(ENTITY);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(ENTITY.getCountry(), result.getCountry()),
                () -> assertEquals(ENTITY.getRegion(), result.getRegion()),
                () -> assertEquals(ENTITY.getCity(), result.getCity()),
                () -> assertEquals(ENTITY.getStreet(), result.getStreet()),
                () -> assertEquals(ENTITY.getHouseNumber(), result.getHouseNumber()),
                () -> assertEquals(ENTITY.getIndex(), result.getIndex())
        );
    }

    @Test
    @DisplayName("маппинг в dto, на вход подан null, негативный тест")
    void toDtoNegativeTest() {
        RegistrationDto result = MAPPER.toDto(null);

        assertNull(result);
    }

    @Test
    @DisplayName("слияние в entity, на вход подан dto и entity, позитивный тест")
    void mergeToEntityPositiveTest() {
        RegistrationEntity result = MAPPER.mergeToEntity(DTO, ENTITY);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(ENTITY.getCountry(), result.getCountry()),
                () -> assertEquals(ENTITY.getRegion(), result.getRegion()),
                () -> assertEquals(ENTITY.getCity(), result.getCity()),
                () -> assertEquals(ENTITY.getStreet(), result.getStreet()),
                () -> assertEquals(ENTITY.getHouseNumber(), result.getHouseNumber()),
                () -> assertEquals(ENTITY.getIndex(), result.getIndex())
        );
    }

    @Test
    @DisplayName("слияние в entity, на вход подан null, негативный тест")
    void mergeToEntityNegativeTest() {
        RegistrationEntity result = MAPPER.mergeToEntity(null, null);

        assertNull(result);
    }

    @Test
    @DisplayName("маппинг в List<dto>, на вход подан List<entity>, позитивный тест")
    void toDtoListPositiveTest() {
        List<RegistrationEntity> entityList = new ArrayList<>();
        entityList.add(ENTITY);

        List<RegistrationDto> result = MAPPER.toDtoList(entityList);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(ENTITY.getId(), result.get(0).getId()),
                () -> assertEquals(ENTITY.getCountry(), result.get(0).getCountry()),
                () -> assertEquals(ENTITY.getRegion(), result.get(0).getRegion()),
                () -> assertEquals(ENTITY.getCity(), result.get(0).getCity()),
                () -> assertEquals(ENTITY.getStreet(), result.get(0).getStreet()),
                () -> assertEquals(ENTITY.getHouseNumber(), result.get(0).getHouseNumber()),
                () -> assertEquals(ENTITY.getIndex(), result.get(0).getIndex())
        );
    }

    @Test
    @DisplayName("маппинг в List<dto>, на вход подан null, негативный тест")
    void toDtoListNegativeTest() {
        List<RegistrationDto> result = MAPPER.toDtoList(null);

        assertNull(result);
    }
}