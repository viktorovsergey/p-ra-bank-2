package com.bank.account.mapper;

import com.bank.account.dto.AccountDetailsDto;
import com.bank.account.entity.AccountDetailsEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Тест класса AccountDetailsMapper")
@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class AccountDetailsMapperTest {

    final AccountDetailsMapper MAPPER = Mappers.getMapper(AccountDetailsMapper.class);
    final AccountDetailsDto ACCOUNT_DETAILS_DTO = new AccountDetailsDto(1L, 2L, 3L,
            4L, new BigDecimal(4), false, 7L);
    final AccountDetailsEntity ACCOUNT_DETAILS_ENTITY = new AccountDetailsEntity(1L, 2L, 3L,
            4L, new BigDecimal(4), false, 7L);
    private final List<AccountDetailsEntity> LIST_ENTITY = List.of(ACCOUNT_DETAILS_ENTITY);
    private final List<AccountDetailsDto> LIST_DTO = List.of(ACCOUNT_DETAILS_DTO);


    @Test
    @DisplayName("маппинг в entity, на вход подан ДТО, позитивный тест")
    void shouldMapDtoToEntityTest() {
        AccountDetailsEntity result = MAPPER.toEntity(ACCOUNT_DETAILS_DTO);
        assertNotNull(result);
        assertThat(result.getPassportId()).isEqualTo(ACCOUNT_DETAILS_ENTITY.getPassportId());
        assertNull(result.getId());
    }

    @Test
    @DisplayName("маппинг в entity, на вход подан null, негативный тест")
    void shouldMapNullDtoToEntityTest() {
        AccountDetailsEntity result = MAPPER.toEntity(null);
        assertNull(result);
    }

    @Test
    @DisplayName("маппинг в дто, на вход подан entity, позитивный тест")
    void shouldMapEntityToDtoTest() {
        AccountDetailsDto result = MAPPER.toDto(ACCOUNT_DETAILS_ENTITY);
        assertNotNull(result);
        assertThat(result.getPassportId()).isEqualTo(ACCOUNT_DETAILS_DTO.getPassportId());
        assertNotNull(result.getId());
    }

    @Test
    @DisplayName("маппинг в дто, на вход подан null, негативный тест")
    void shouldMapNullEntityToDtoTest() {
        AccountDetailsDto result = MAPPER.toDto(null);
        assertNull(result);
    }

    @Test
    @DisplayName("маппинг в dto лист, на вход подан entity_list, позитивный тест")
    void shouldMapDtoListToEntityListTest() {
        List<AccountDetailsDto> result = MAPPER.toDtoList(LIST_ENTITY);
        assertNotNull(result);
        assertThat(result.get(0).getPassportId()).isEqualTo(LIST_DTO.get(0).getPassportId());
        assertNotNull(result.get(0).getId());
    }

    @Test
    @DisplayName("маппинг в dto лист, на вход подан null, негативный тест")
    void shouldMapNullEntityListToDtoListTest() {
        List<AccountDetailsDto> result = MAPPER.toDtoList(null);
        assertNull(result);
    }

    @Test
    @DisplayName("слияние в entity, на вход поданы dto и entity, позитивный тест")
    void mergeToEntityTest() {
        AccountDetailsEntity result = MAPPER.mergeToEntity(ACCOUNT_DETAILS_ENTITY, ACCOUNT_DETAILS_DTO);
        assertNotNull(result);
        assertThat(result.getPassportId()).isEqualTo(ACCOUNT_DETAILS_DTO.getPassportId());
        assertNotNull(result.getId());
    }

    @Test
    @DisplayName("слияние в entity, на вход подан null, негативный тест")
    void mergeToEntityNullTest() {
        AccountDetailsEntity result = MAPPER.mergeToEntity(null, null);
        assertNull(result);
    }
}