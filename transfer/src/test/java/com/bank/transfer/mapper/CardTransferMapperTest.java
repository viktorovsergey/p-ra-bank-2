package com.bank.transfer.mapper;

import com.bank.transfer.dto.CardTransferDto;
import com.bank.transfer.entity.CardTransferEntity;
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
class CardTransferMapperTest {
    private final CardTransferMapper cardTransferMapper =
            Mappers.getMapper(CardTransferMapper.class);
    private final CardTransferDto cardTransferDto =
            new CardTransferDto(null, 1111_2222_3333_4444L, BigDecimal.valueOf(1),
                    "1", 1L);
    private final CardTransferEntity cardTransferEntity =
            new CardTransferEntity(null, 1111_2222_3333_4444L, BigDecimal.valueOf(1),
                    "1", 1L);

    @Test
    @DisplayName("Маппинг cardTransferDto в entity")
    void toEntityTest() {
        CardTransferEntity resultCardTransferEntity = cardTransferMapper.toEntity(cardTransferDto);
        assertAll(
                () -> assertNotNull(resultCardTransferEntity),
                () -> assertEquals(cardTransferEntity, resultCardTransferEntity)
        );
    }

    @Test
    @DisplayName("Маппинг cardTransferDto в entity, на вход подан null")
    void noEntityNullTest() {
        assertNull(cardTransferMapper.toEntity(null));
    }

    @Test
    @DisplayName("Маппинг cardTransferEntity в dto")
    void toDtoTest() {
        assertEquals(cardTransferDto, cardTransferMapper.toDto(cardTransferEntity));
    }

    @Test
    @DisplayName("Маппинг cardTransferEntity в dto, на вход подан null")
    void toDtoNullTest() {
        assertNull(cardTransferMapper.toDto(null));
    }

    @Test
    @DisplayName("Слияние cardTransfer в entity")
    void mergeToEntityTest() {
        CardTransferEntity existingCardEntity = new CardTransferEntity();
        CardTransferEntity resultCardTransferEntity =
                cardTransferMapper.mergeToEntity(cardTransferDto, existingCardEntity);
        assertAll(
                () -> assertNotNull(resultCardTransferEntity),
                () -> assertEquals(resultCardTransferEntity, cardTransferEntity),
                () -> assertNull(resultCardTransferEntity.getId())
        );
    }

    @Test
    @DisplayName("Слияние CardTransfer в entity, на вход подан null")
    void mergeToEntityNullTest() {
        assertNull(cardTransferMapper.mergeToEntity(null, null));
    }

    @Test
    @DisplayName("Маппинг списка cardTransferEntity в список cardTransferDto")
    void toDtoListPositiveTest() {
        List<CardTransferEntity> cardTransferEntityList = new ArrayList<>();
        cardTransferEntityList.add(new CardTransferEntity(1L, 1L,
                BigDecimal.valueOf(1), "1", 1L));
        cardTransferEntityList.add(new CardTransferEntity(1L, 2L,
                BigDecimal.valueOf(2), "2", 2L));
        cardTransferEntityList.add(new CardTransferEntity(1L, 3L,
                BigDecimal.valueOf(3), "3", 3L));
        List<CardTransferDto> resultListCardTransferDto =
                cardTransferMapper.toDtoList(cardTransferEntityList);
        assertAll(
                () -> assertNotNull(resultListCardTransferDto),
                () -> assertEquals(3, resultListCardTransferDto.size()),
                () -> assertEquals(new CardTransferDto(1L, 1L,
                                BigDecimal.valueOf(1), "1", 1L),
                        resultListCardTransferDto.get(0)),
                () -> assertEquals(new CardTransferDto(1L, 2L,
                                BigDecimal.valueOf(2), "2", 2L),
                        resultListCardTransferDto.get(1)),
                () -> assertEquals(new CardTransferDto(1L, 3L,
                                BigDecimal.valueOf(3), "3", 3L),
                        resultListCardTransferDto.get(2))
        );
    }

    @Test
    @DisplayName("Маппинг списка CardTransferEntity в список CardTransferDto, на вход подается null")
    void toDtoListNullTest() {
        assertNull(cardTransferMapper.toDtoList(null));
    }
}