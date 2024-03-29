package com.bank.transfer.mapper;

import com.bank.transfer.dto.PhoneTransferDto;
import com.bank.transfer.entity.PhoneTransferEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class PhoneTransferMapperTest {
    private final PhoneTransferMapper phoneTransferMapper =
            Mappers.getMapper(PhoneTransferMapper.class);
    private final PhoneTransferDto phoneTransferDto = new PhoneTransferDto(null, 89000000000L,
            BigDecimal.valueOf(1), "1", 2L);
    private final PhoneTransferEntity phoneTransferEntity =
            new PhoneTransferEntity(null, 89000000000L,
                    BigDecimal.valueOf(1), "1", 2L);

    @Test
    @DisplayName("Маппинг phoneTransferDto в entity")
    void toEntityTest() {
        PhoneTransferEntity resultPhoneTransferEntity =
                phoneTransferMapper.toEntity(phoneTransferDto);

        assertAll(
                () -> assertNotNull(resultPhoneTransferEntity),
                () -> assertEquals(phoneTransferEntity, resultPhoneTransferEntity)
        );
    }

    @Test
    @DisplayName("Маппинг phoneTransferDto в entity, на вход подан null")
    void noEntityNullTest() {
        PhoneTransferEntity resultPhoneTransferEntityNull = phoneTransferMapper.toEntity(null);
        assertNull(resultPhoneTransferEntityNull);
    }

    @Test
    @DisplayName("Маппинг phoneTransferEntity в dto")
    void toDtoTest() {
        PhoneTransferDto resultPhoneTransferDto = phoneTransferMapper.toDto(phoneTransferEntity);

        assertAll(
                () -> assertNotNull(resultPhoneTransferDto),
                () -> assertEquals(resultPhoneTransferDto, phoneTransferDto)
        );
    }

    @Test
    @DisplayName("Маппинг phoneTransferEntity в dto, на вход подан null")
    void toDtoNullTest() {
        PhoneTransferDto resultPhoneTransferDtoNull = phoneTransferMapper.toDto(null);
        assertNull(resultPhoneTransferDtoNull);
    }

    @Test
    @DisplayName("Слияние phoneTransferEntity в entity")
    void mergeToEntityTest() {
        PhoneTransferEntity existingPhoneTransferEntity = new PhoneTransferEntity();
        PhoneTransferEntity resultPhoneTransferEntity =
                phoneTransferMapper.mergeToEntity(phoneTransferDto,
                        existingPhoneTransferEntity);

        assertAll(
                () -> assertNotNull(resultPhoneTransferEntity),
                () -> assertEquals(resultPhoneTransferEntity, phoneTransferEntity),
                () -> assertNull(resultPhoneTransferEntity.getId())
        );
    }

    @Test
    @DisplayName("Слияние phoneTransferEntity в entity, на вход подан null")
    void mergeToEntityNullTest() {
        PhoneTransferEntity resultPhoneTransferEntityNull = phoneTransferMapper.mergeToEntity(null,
                null);
        assertNull(resultPhoneTransferEntityNull);
    }

    @Test
    @DisplayName("Маппинг списка phoneTransferEntity в список PhoneTransferDto")
    void toDtoListTest() {
        List<PhoneTransferEntity> phoneTransferEntityList = new ArrayList<>();
        phoneTransferEntityList.add(new PhoneTransferEntity(1L, 89111111111L,
                BigDecimal.valueOf(1), "1", 1L));
        phoneTransferEntityList.add(new PhoneTransferEntity(2L, 89222222222L,
                BigDecimal.valueOf(2), "2", 2L));
        phoneTransferEntityList.add(new PhoneTransferEntity(3L, 89333333333L,
                BigDecimal.valueOf(3), "3", 3L));
        List<PhoneTransferDto> resultListPhoneTransferDto =
                phoneTransferMapper.toDtoList(phoneTransferEntityList);

        assertAll(
                () -> assertNotNull(resultListPhoneTransferDto),
                () -> assertEquals(3, resultListPhoneTransferDto.size()),
                () -> assertEquals(new PhoneTransferDto(1L, 89111111111L, BigDecimal.valueOf(1),
                        "1", 1L), resultListPhoneTransferDto.get(0)),
                () -> assertEquals(new PhoneTransferDto(2L, 89222222222L, BigDecimal.valueOf(2),
                        "2", 2L), resultListPhoneTransferDto.get(1)),
                () -> assertEquals(new PhoneTransferDto(3L, 89333333333L, BigDecimal.valueOf(3),
                        "3", 3L), resultListPhoneTransferDto.get(2))
        );
    }

    @Test
    @DisplayName("Маппинг списка PhoneTransferEntity в список PhoneTransferDto, на вход подается null")
    void toDtoListNullTest() {
        List<PhoneTransferDto> resultListPhoneTransferDtoNull = phoneTransferMapper.toDtoList(null);
        assertNull(resultListPhoneTransferDtoNull);
    }
}