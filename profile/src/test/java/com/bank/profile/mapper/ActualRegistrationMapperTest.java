package com.bank.profile.mapper;

import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.entity.ActualRegistrationEntity;
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
@DisplayName("Тест класса ActualRegistrationMapper")
class ActualRegistrationMapperTest {
    final ActualRegistrationEntity ENTITY = new ActualRegistrationEntity(1L, "Russia", "Altaysky kray",
            "Barnaul", null, null, "prospect Lenina", "56",
            null, null, 656000L);
    final ActualRegistrationDto DTO = new ActualRegistrationDto(1L, "Russia", "Altaysky kray",
            "Barnaul", null, null, "prospect Lenina", "56",
            null, null, 656000L);
    final ActualRegistrationMapper MAPPER = Mappers.getMapper(ActualRegistrationMapper.class);

    @Test
    @DisplayName("маппинг в entity, на вход подан dto, позитивный тест")
    void toEntityPositiveTest() {
        ActualRegistrationEntity result = MAPPER.toEntity(DTO);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(ENTITY.getRegion(), result.getRegion()),
                () -> assertEquals(ENTITY.getDistrict(), result.getDistrict())
        );

    }

    @Test
    @DisplayName("маппинг в entity, на вход подан null, негативный тест")
    void toEntityNegativeTest() {
        ActualRegistrationEntity result = MAPPER.toEntity(null);

        assertNull(result);
    }

    @Test
    @DisplayName("маппинг в dto, на вход подан entity, позитивный тест")
    void toDtoPositiveTest() {
        ActualRegistrationDto result = MAPPER.toDto(ENTITY);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(ENTITY.getId(), result.getId()),
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
        ActualRegistrationDto result = MAPPER.toDto(null);

        assertNull(result);
    }

    @Test
    @DisplayName("слияние в entity, на вход подан dto и entity, позитивный тест")
    void mergeToEntityPositiveTest() {
        ActualRegistrationEntity result = MAPPER.mergeToEntity(DTO, ENTITY);

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
        ActualRegistrationEntity result = MAPPER.mergeToEntity(null, null);

        assertNull(result);
    }

    @Test
    @DisplayName("маппинг в List<dto>, на вход подан List<entity>, позитивный тест")
    void toDtoListPositiveTest() {
        List<ActualRegistrationEntity> entityList = new ArrayList<>();
        entityList.add(ENTITY);

        List<ActualRegistrationDto> result = MAPPER.toDtoList(entityList);

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
        List<ActualRegistrationDto> result = MAPPER.toDtoList(null);

        assertNull(result);
    }
}