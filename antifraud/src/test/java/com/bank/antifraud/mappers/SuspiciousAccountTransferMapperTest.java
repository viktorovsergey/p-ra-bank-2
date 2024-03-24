package com.bank.antifraud.mappers;

import com.bank.antifraud.dto.SuspiciousAccountTransferDto;
import com.bank.antifraud.entity.SuspiciousAccountTransferEntity;
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
class SuspiciousAccountTransferMapperTest {

    @Mock
    SuspiciousAccountTransferEntity entity;
    @Mock
    SuspiciousAccountTransferDto dto;
    @InjectMocks
    SuspiciousAccountTransferMapperImpl mapper;

    @Test
    @DisplayName("маппинг в dto")
    void toDtoTest() {
        when(entity.getId()).thenReturn(1L);
        when(entity.getAccountTransferId()).thenReturn(1L);
        when(entity.getIsSuspicious()).thenReturn(true);
        when(entity.getSuspiciousReason()).thenReturn("suspiciousReason1");
        when(entity.getBlockedReason()).thenReturn("blockedReason1");
        when(entity.getIsBlocked()).thenReturn(true);

        SuspiciousAccountTransferDto result = mapper.toDto(entity);

        assertAll(() -> assertThat(result.getId()).isEqualTo(1L),
                () -> assertThat(result.getAccountTransferId()).isEqualTo(1L),
                () -> assertThat(result.getIsSuspicious()).isTrue(),
                () -> assertThat(result.getSuspiciousReason()).isEqualTo("suspiciousReason1"),
                () -> assertThat(result.getBlockedReason()).isEqualTo("blockedReason1"),
                () -> assertThat(result.getIsBlocked()).isTrue());
    }

    @Test
    @DisplayName("маппинг в dto, на вход подан null")
    void noDtoNullTest() {
        SuspiciousAccountTransferEntity nullEntity = null;

        SuspiciousAccountTransferDto result = mapper.toDto(nullEntity);

        assertThat(result).isNull();
    }

    @Test
    @DisplayName("маппинг в entity")
    void toEntityTest() {
        when(dto.getAccountTransferId()).thenReturn(1L);
        when(dto.getIsBlocked()).thenReturn(true);
        when(dto.getIsSuspicious()).thenReturn(true);
        when(dto.getBlockedReason()).thenReturn("blockedReason1");
        when(dto.getSuspiciousReason()).thenReturn("suspiciousReason1");

        SuspiciousAccountTransferEntity result = mapper.toEntity(dto);

        assertAll(() -> assertThat(result.getAccountTransferId()).isEqualTo(1L),
                () -> assertThat(result.getIsSuspicious()).isTrue(),
                () -> assertThat(result.getSuspiciousReason()).isEqualTo("suspiciousReason1"),
                () -> assertThat(result.getBlockedReason()).isEqualTo("blockedReason1"),
                () -> assertThat(result.getIsBlocked()).isTrue());
    }

    @Test
    @DisplayName("маппинг в entity, на вход подан null")
    void noEntityNullTest() {
        SuspiciousAccountTransferDto nullDto = null;

        SuspiciousAccountTransferEntity result = mapper.toEntity(nullDto);

        assertThat(result).isNull();
    }

    @Test
    @DisplayName("маппинг в коллекцию dto")
    void toListDtoTest() {
        List<SuspiciousAccountTransferEntity> entityList = new ArrayList<>();
        SuspiciousAccountTransferEntity entity1 = new SuspiciousAccountTransferEntity();
        entity1.setId(1L);
        entityList.add(entity1);
        SuspiciousAccountTransferEntity entity2 = new SuspiciousAccountTransferEntity();
        entity2.setId(2L);
        entityList.add(entity2);

        List<SuspiciousAccountTransferDto> result = mapper.toListDto(entityList);

        assertAll(() -> assertThat(result).isNotNull(),
                () -> assertThat(result).hasSize(entityList.size()),
                () -> assertThat(result.get(0).getId()).isEqualTo(entityList.get(0).getId()),
                () -> assertThat(result.get(1).getId()).isEqualTo(entityList.get(1).getId()));

    }

    @Test
    @DisplayName("маппинг в коллекцию dto, на вход подан null")
    void noListDtoNullTest() {
        List<SuspiciousAccountTransferEntity> entityList = null;

        List<SuspiciousAccountTransferDto> result = mapper.toListDto(entityList);

        assertThat(result).isNull();
    }

    @Test
    @DisplayName("слияние в entity")
    void mergeToEntityTest() {
        SuspiciousAccountTransferEntity actualEntity = new SuspiciousAccountTransferEntity();
        actualEntity.setAccountTransferId(1L);
        actualEntity.setIsBlocked(true);
        actualEntity.setIsSuspicious(true);
        actualEntity.setBlockedReason("blockedReason");
        actualEntity.setSuspiciousReason("suspiciousReason");
        SuspiciousAccountTransferDto actualDto = new SuspiciousAccountTransferDto();
        actualDto.setAccountTransferId(1L);
        actualDto.setIsBlocked(false);
        actualDto.setIsSuspicious(false);
        actualDto.setBlockedReason("updatedBlockedReason");
        actualDto.setSuspiciousReason("updatedSuspiciousReason");

        SuspiciousAccountTransferEntity result = mapper.mergeToEntity(actualDto, actualEntity);

        assertAll("Check entity properties",
                () -> assertThat(result).isSameAs(actualEntity),
                () -> assertThat(result.getAccountTransferId()).isEqualTo(1L),
                () -> assertThat(result.getIsBlocked()).isFalse(),
                () -> assertThat(result.getIsSuspicious()).isFalse(),
                () -> assertThat(result.getBlockedReason()).isEqualTo("updatedBlockedReason"),
                () -> assertThat(result.getSuspiciousReason()).isEqualTo("updatedSuspiciousReason")
        );
    }

    @Test
    @DisplayName("слияние в entity, на вход подан null")
    void mergeToEntityNullTest() {
        SuspiciousAccountTransferDto nullDto = null;
        SuspiciousAccountTransferEntity actualEntity = new SuspiciousAccountTransferEntity();
        actualEntity.setAccountTransferId(1L);
        actualEntity.setIsBlocked(true);
        actualEntity.setIsSuspicious(true);
        actualEntity.setBlockedReason("blockedReason");
        actualEntity.setSuspiciousReason("suspiciousReason");

        SuspiciousAccountTransferEntity result = mapper.mergeToEntity(nullDto, actualEntity);

        assertThat(result).isSameAs(actualEntity);
    }
}