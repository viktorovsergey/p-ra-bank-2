package com.bank.antifraud.mappers;

import com.bank.antifraud.dto.SuspiciousCardTransferDto;
import com.bank.antifraud.entity.SuspiciousCardTransferEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class SuspiciousCardTransferMapperImplTest {
    @Mock
    SuspiciousCardTransferEntity entity;
    @Mock
    SuspiciousCardTransferDto dto;
    @InjectMocks
    SuspiciousCardTransferMapperImpl mapper;

    @Test
    @DisplayName("маппинг в dto")
    void toDtoTest() {
        when(entity.getId()).thenReturn(1L);
        when(entity.getCardTransferId()).thenReturn(1L);
        when(entity.getIsSuspicious()).thenReturn(true);
        when(entity.getSuspiciousReason()).thenReturn("suspiciousReason1");
        when(entity.getBlockedReason()).thenReturn("blockedReason1");
        when(entity.getIsBlocked()).thenReturn(true);

        SuspiciousCardTransferDto result = mapper.toDto(entity);

        assertAll(() -> assertThat(result.getId()).isEqualTo(1L),
                () -> assertThat(result.getCardTransferId()).isEqualTo(1L),
                () -> assertThat(result.getIsSuspicious()).isTrue(),
                () -> assertThat(result.getSuspiciousReason()).isEqualTo("suspiciousReason1"),
                () -> assertThat(result.getBlockedReason()).isEqualTo("blockedReason1"),
                () -> assertThat(result.getIsBlocked()).isTrue());
    }

    @Test
    @DisplayName("маппинг в dto, на вход подан null")
    void noDtoNullTest() {
        SuspiciousCardTransferEntity nullEntity = null;

        SuspiciousCardTransferDto result = mapper.toDto(nullEntity);

        assertThat(result).isNull();
    }

    @Test
    @DisplayName("маппинг в entity")
    void toEntityTest() {
        when(dto.getCardTransferId()).thenReturn(1L);
        when(dto.getIsBlocked()).thenReturn(true);
        when(dto.getIsSuspicious()).thenReturn(true);
        when(dto.getBlockedReason()).thenReturn("blockedReason1");
        when(dto.getSuspiciousReason()).thenReturn("suspiciousReason1");

        SuspiciousCardTransferEntity result = mapper.toEntity(dto);

        assertAll(() -> assertThat(result.getCardTransferId()).isEqualTo(1L),
                () -> assertThat(result.getIsSuspicious()).isTrue(),
                () -> assertThat(result.getSuspiciousReason()).isEqualTo("suspiciousReason1"),
                () -> assertThat(result.getBlockedReason()).isEqualTo("blockedReason1"),
                () -> assertThat(result.getIsBlocked()).isTrue());
    }

    @Test
    @DisplayName("маппинг в entity, на вход подан null")
    void noEntityNullTest() {
        SuspiciousCardTransferDto nullDto = null;

        SuspiciousCardTransferEntity result = mapper.toEntity(nullDto);

        assertThat(result).isNull();
    }

    @Test
    @DisplayName("маппинг в коллекцию dto")
    void toListDtoTest() {
        List<SuspiciousCardTransferEntity> entityList = new ArrayList<>();
        SuspiciousCardTransferEntity entity1 = new SuspiciousCardTransferEntity();
        entity1.setId(1L);
        entityList.add(entity1);
        SuspiciousCardTransferEntity entity2 = new SuspiciousCardTransferEntity();
        entity2.setId(2L);
        entityList.add(entity2);

        List<SuspiciousCardTransferDto> result = mapper.toListDto(entityList);

        assertAll(() -> assertThat(result).isNotNull(),
                () -> assertThat(result).hasSize(entityList.size()),
                () -> assertThat(result.get(0).getId()).isEqualTo(entityList.get(0).getId()),
                () -> assertThat(result.get(1).getId()).isEqualTo(entityList.get(1).getId()));
    }

    @Test
    @DisplayName("маппинг в коллекцию dto, на вход подан null")
    void noListDtoNullTest() {
        List<SuspiciousCardTransferEntity> entityList = null;

        List<SuspiciousCardTransferDto> result = mapper.toListDto(entityList);

        assertThat(result).isNull();
    }

    @Test
    @DisplayName("слияние в entity")
    void mergeToEntityTest() {
        SuspiciousCardTransferEntity actualEntity = new SuspiciousCardTransferEntity();
        actualEntity.setCardTransferId(1L);
        actualEntity.setIsBlocked(true);
        actualEntity.setIsSuspicious(true);
        actualEntity.setBlockedReason("blockedReason");
        actualEntity.setSuspiciousReason("suspiciousReason");
        SuspiciousCardTransferDto actualDto = new SuspiciousCardTransferDto();
        actualDto.setCardTransferId(1L);
        actualDto.setIsBlocked(false);
        actualDto.setIsSuspicious(false);
        actualDto.setBlockedReason("updatedBlockedReason");
        actualDto.setSuspiciousReason("updatedSuspiciousReason");

        SuspiciousCardTransferEntity result = mapper.mergeToEntity(actualDto, actualEntity);

        assertAll("Check entity properties",
                () -> assertThat(result).isSameAs(actualEntity),
                () -> assertThat(result.getCardTransferId()).isEqualTo(1L),
                () -> assertThat(result.getIsBlocked()).isFalse(),
                () -> assertThat(result.getIsSuspicious()).isFalse(),
                () -> assertThat(result.getBlockedReason()).isEqualTo("updatedBlockedReason"),
                () -> assertThat(result.getSuspiciousReason()).isEqualTo("updatedSuspiciousReason")
        );
    }

    @Test
    @DisplayName("слияние в entity, на вход подан null")
    void mergeToEntityNullTest() {
        SuspiciousCardTransferDto nullDto = null;
        SuspiciousCardTransferEntity actualEntity = new SuspiciousCardTransferEntity();
        actualEntity.setCardTransferId(1L);
        actualEntity.setIsBlocked(true);
        actualEntity.setIsSuspicious(true);
        actualEntity.setBlockedReason("blockedReason");
        actualEntity.setSuspiciousReason("suspiciousReason");

        SuspiciousCardTransferEntity result = mapper.mergeToEntity(nullDto, actualEntity);

        assertThat(result).isSameAs(actualEntity);
    }
}