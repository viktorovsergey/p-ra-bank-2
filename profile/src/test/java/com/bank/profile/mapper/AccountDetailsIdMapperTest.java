package com.bank.profile.mapper;

import com.bank.profile.dto.AccountDetailsIdDto;
import com.bank.profile.dto.ProfileDto;
import com.bank.profile.entity.AccountDetailsIdEntity;
import com.bank.profile.entity.ProfileEntity;
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
@DisplayName("Тест класса AccountDetailsIdMapper")
class AccountDetailsIdMapperTest {

    final AccountDetailsIdEntity ENTITY = new AccountDetailsIdEntity(1L, 1L, new ProfileEntity());

    final AccountDetailsIdDto DTO = new AccountDetailsIdDto(1L, 1L, new ProfileDto());

    final AccountDetailsIdMapper MAPPER = Mappers.getMapper(AccountDetailsIdMapper.class);

    @Test
    @DisplayName("маппинг в entity, на вход подан dto, позитивный тест")
    void toEntityPositiveTest() {
        AccountDetailsIdEntity result = MAPPER.toEntity(DTO);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(DTO.getAccountId(), result.getAccountId())
        );
    }

    @Test
    @DisplayName("маппинг в entity, на вход подан null, негативный тест")
    void toEntityNegativeTest() {
        AccountDetailsIdEntity result = MAPPER.toEntity(null);

        assertNull(result);
    }

    @Test
    @DisplayName("маппинг в dto, на вход подан entity, позитивный тест")
    void toDtoPositiveTest() {
        AccountDetailsIdDto result = MAPPER.toDto(ENTITY);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(ENTITY.getId(), result.getId()),
                () -> assertEquals(ENTITY.getAccountId(), result.getAccountId())
        );
    }

    @Test
    @DisplayName("маппинг в dto, на вход подан null, негативный тест")
    void toDtoNegativeTest() {
        AccountDetailsIdDto result = MAPPER.toDto(null);

        assertNull(result);
    }

    @Test
    @DisplayName("слияние в entity, на вход подан dto и entity, позитивный тест")
    void mergeToEntityPositiveTest() {
        AccountDetailsIdEntity result = MAPPER.mergeToEntity(DTO, ENTITY);

        assertAll(
                () -> assertNotNull(result.getId()),
                () -> assertEquals(ENTITY.getId(), result.getId()),
                () -> assertEquals(ENTITY.getAccountId(), result.getAccountId())
        );
    }

    @Test
    @DisplayName("слияние в entity, на вход подан null, негативный тест")
    void mergeToEntityNegativeTest() {
        AccountDetailsIdEntity result = MAPPER.mergeToEntity(null, null);

        assertNull(result);
    }

    @Test
    @DisplayName("маппинг в List<dto>, на вход подан List<entity>, позитивный тест")
    void toDtoListPositiveTest() {
        List<AccountDetailsIdEntity> entityList = new ArrayList<>();
        entityList.add(ENTITY);

        List<AccountDetailsIdDto> result = MAPPER.toDtoList(entityList);

        assertAll(
                () -> assertNotNull(result.get(0).getId()),
                () -> assertEquals(ENTITY.getId(), result.get(0).getId()),
                () -> assertEquals(ENTITY.getAccountId(), result.get(0).getAccountId())
        );
    }

    @Test
    @DisplayName("маппинг в List<dto>, на вход подан null, негативный тест")
    void toDtoListNegativeTest() {
        List<AccountDetailsIdDto> result = MAPPER.toDtoList(null);

        assertNull(result);
    }
}