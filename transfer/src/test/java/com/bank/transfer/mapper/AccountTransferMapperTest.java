package com.bank.transfer.mapper;

import com.bank.transfer.dto.AccountTransferDto;
import com.bank.transfer.entity.AccountTransferEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mapstruct.factory.Mappers.getMapper;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class AccountTransferMapperTest {
    private final AccountTransferMapper accountTransferMapper =
            getMapper(AccountTransferMapper.class);
    private final AccountTransferDto accountTransferDto =
            new AccountTransferDto(1L, 1L, BigDecimal.valueOf(1),
                    "1", 1L);
    private final AccountTransferEntity accountTransferEntity =
            new AccountTransferEntity(1L, 1L, BigDecimal.valueOf(1),
                    "1", 1L);
    private final AccountTransferEntity accountTransferEntity1 =
            new AccountTransferEntity(1L, 1L, BigDecimal.valueOf(1),
                    "1", 1L);

    @Test
    @DisplayName("Маппинг accountTransferDto в entity")
    void toEntityTest() {
        AccountTransferEntity accountTransferEntity =
                accountTransferMapper.toEntity(accountTransferDto);

        assertAll(
                () -> assertEquals(this.accountTransferEntity.getAccountNumber(),
                        accountTransferEntity.getAccountNumber()),
                () -> assertEquals(this.accountTransferEntity.getAmount(),
                        accountTransferEntity.getAmount()),
                () -> assertEquals(this.accountTransferEntity.getPurpose(),
                        accountTransferEntity.getPurpose()),
                () -> assertEquals(this.accountTransferEntity.getAccountDetailsId(),
                        accountTransferEntity.getAccountDetailsId()));
    }

    @Test
    @DisplayName("Маппинг accountTransferDto в entity, на вход подан null")
    void noEntityNullTest() {
        assertNull(accountTransferMapper.toEntity(null));
    }

    @Test
    @DisplayName("Маппинг accountTransferEntity в dto")
    void toDtoTest() {
        AccountTransferDto accountTransferDto = accountTransferMapper.toDto(accountTransferEntity);

        assertAll(() -> assertEquals(this.accountTransferDto.getId(),
                        accountTransferDto.getAccountNumber()),
                () -> assertEquals(this.accountTransferDto.getAmount(),
                        accountTransferDto.getAmount()),
                () -> assertEquals(this.accountTransferDto.getPurpose(),
                        accountTransferDto.getPurpose()),
                () -> assertEquals(this.accountTransferDto.getAccountDetailsId(),
                        accountTransferDto.getAccountDetailsId()));
    }

    @Test
    @DisplayName("Маппинг accountTransferEntity в dto, на вход подан null")
    void toDtoNullTest() {
        assertNull(accountTransferMapper.toDto(null));
    }

    @Test
    @DisplayName("Слияние accountTransfer в entity")
    void mergeToEntityTest() {
        AccountTransferEntity existingTransferEntity = new AccountTransferEntity();
        AccountTransferEntity accountTransferEntity =
                accountTransferMapper.mergeToEntity(accountTransferDto, existingTransferEntity);

        assertAll(
                () -> assertNotNull(accountTransferEntity),
                () -> assertEquals(accountTransferEntity1.getAccountNumber(),
                        accountTransferEntity.getAccountNumber()),
                () -> assertEquals(accountTransferEntity1.getAmount(),
                        accountTransferEntity.getAmount()),
                () -> assertEquals(accountTransferEntity1.getPurpose(),
                        accountTransferEntity.getPurpose()),
                () -> assertEquals(accountTransferEntity1.getAccountDetailsId(),
                        accountTransferEntity.getAccountDetailsId()));
    }

    @Test
    @DisplayName("Слияние accountTransfer в entity, на вход подан null")
    void mergeToEntityNullTest() {
        assertNull(accountTransferMapper.mergeToEntity(null, null));
    }

    @Test
    @DisplayName("Маппинг списка accountTransferEntity в список accountTransferDto")
    void toDtoListTest() {
        List<AccountTransferEntity> accountTransferEntityList = new ArrayList<>();

        accountTransferEntityList.add(
                new AccountTransferEntity(1L, 1L, BigDecimal.valueOf(1),
                        "1", 1L));
        accountTransferEntityList.add(
                new AccountTransferEntity(2L, 2L, BigDecimal.valueOf(2),
                        "2", 2L));
        accountTransferEntityList.add(
                new AccountTransferEntity(3L, 3L, BigDecimal.valueOf(3),
                        "3", 3L));

        List<AccountTransferDto> accountTransferDtoList =
                accountTransferMapper.toDtoList(accountTransferEntityList);

        assertNotNull(accountTransferDtoList);
        assertAll(
                () -> assertEquals(3, accountTransferDtoList.size()), () -> assertEquals(
                        new AccountTransferEntity(1L, 1L, BigDecimal.valueOf(1),
                                "1", 1L),
                        accountTransferEntityList.get(0)), () -> assertEquals(
                        new AccountTransferEntity(2L, 2L, BigDecimal.valueOf(2),
                                "2", 2L),
                        accountTransferEntityList.get(1)), () -> assertEquals(
                        new AccountTransferEntity(3L, 3L, BigDecimal.valueOf(3),
                                "3", 3L),
                        accountTransferEntityList.get(2)));
    }

    @Test
    @DisplayName("Маппинг списка accountTransferEntity в список accountTransferDto, на вход подается null")
    void toDtoListNullTest() {
        assertNull(accountTransferMapper.toDtoList(null));
    }
}