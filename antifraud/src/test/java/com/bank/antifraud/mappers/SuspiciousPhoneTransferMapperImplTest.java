package com.bank.antifraud.mappers;

import com.bank.antifraud.dto.SuspiciousPhoneTransferDto;
import com.bank.antifraud.entity.SuspiciousPhoneTransferEntity;
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
class SuspiciousPhoneTransferMapperImplTest {
    @Mock
    SuspiciousPhoneTransferEntity entity;
    @Mock
    SuspiciousPhoneTransferDto dto;
    @InjectMocks
    SuspiciousPhoneTransferMapperImpl mapper;

    @Test
    @DisplayName("маппинг в dto")
    void toDtoTest() {
        when(entity.getId()).thenReturn(1L);
        when(entity.getPhoneTransferId()).thenReturn(1L);
        when(entity.getIsSuspicious()).thenReturn(true);
        when(entity.getSuspiciousReason()).thenReturn("suspiciousReason1");
        when(entity.getBlockedReason()).thenReturn("blockedReason1");
        when(entity.getIsBlocked()).thenReturn(true);

        SuspiciousPhoneTransferDto result = mapper.toDto(entity);

        assertAll(() -> assertThat(result.getId()).isEqualTo(1L),
                () -> assertThat(result.getPhoneTransferId()).isEqualTo(1L),
                () -> assertThat(result.getIsSuspicious()).isTrue(),
                () -> assertThat(result.getSuspiciousReason()).isEqualTo("suspiciousReason1"),
                () -> assertThat(result.getBlockedReason()).isEqualTo("blockedReason1"),
                () -> assertThat(result.getIsBlocked()).isTrue());
    }

    @Test
    @DisplayName("маппинг в dto, на вход подан null")
    void noDtoNullTest() {
        SuspiciousPhoneTransferEntity nullEntity = null;

        SuspiciousPhoneTransferDto result = mapper.toDto(nullEntity);

        assertThat(result).isNull();
    }

    @Test
    @DisplayName("маппинг в entity")
    void toEntityTest() {
        when(dto.getPhoneTransferId()).thenReturn(1L);
        when(dto.getIsBlocked()).thenReturn(true);
        when(dto.getIsSuspicious()).thenReturn(true);
        when(dto.getBlockedReason()).thenReturn("blockedReason1");
        when(dto.getSuspiciousReason()).thenReturn("suspiciousReason1");

        SuspiciousPhoneTransferEntity result = mapper.toEntity(dto);

        assertAll(() -> assertThat(result.getPhoneTransferId()).isEqualTo(1L),
                () -> assertThat(result.getIsSuspicious()).isTrue(),
                () -> assertThat(result.getSuspiciousReason()).isEqualTo("suspiciousReason1"),
                () -> assertThat(result.getBlockedReason()).isEqualTo("blockedReason1"),
                () -> assertThat(result.getIsBlocked()).isTrue());
    }

    @Test
    @DisplayName("маппинг в entity, на вход подан null")
    void noEntityNullTest() {
        SuspiciousPhoneTransferDto nullDto = null;

        SuspiciousPhoneTransferEntity result = mapper.toEntity(nullDto);

        assertThat(result).isNull();
    }

    @Test
    @DisplayName("маппинг в коллекцию dto")
    void toListDtoTest() {
        List<SuspiciousPhoneTransferEntity> entityList = new ArrayList<>();
        SuspiciousPhoneTransferEntity entity1 = new SuspiciousPhoneTransferEntity();
        entity1.setId(1L);
        entityList.add(entity1);
        SuspiciousPhoneTransferEntity entity2 = new SuspiciousPhoneTransferEntity();
        entity2.setId(2L);
        entityList.add(entity2);

        List<SuspiciousPhoneTransferDto> result = mapper.toListDto(entityList);

        assertAll(() -> assertThat(result).isNotNull(),
                () -> assertThat(result).hasSize(entityList.size()),
                () -> assertThat(result.get(0).getId()).isEqualTo(entityList.get(0).getId()),
                () -> assertThat(result.get(1).getId()).isEqualTo(entityList.get(1).getId()));
    }

    @Test
    @DisplayName("маппинг в коллекцию dto, на вход подан null")
    void noListDtoNullTest() {
        List<SuspiciousPhoneTransferEntity> entityList = null;

        List<SuspiciousPhoneTransferDto> result = mapper.toListDto(entityList);

        assertThat(result).isNull();
    }

    @Test
    @DisplayName("слияние в entity")
    void mergeToEntityTest() {
        SuspiciousPhoneTransferEntity actualEntity = new SuspiciousPhoneTransferEntity();
        actualEntity.setPhoneTransferId(1L);
        actualEntity.setIsBlocked(true);
        actualEntity.setIsSuspicious(true);
        actualEntity.setBlockedReason("blockedReason");
        actualEntity.setSuspiciousReason("suspiciousReason");
        SuspiciousPhoneTransferDto actualDto = new SuspiciousPhoneTransferDto();
        actualDto.setPhoneTransferId(1L);
        actualDto.setIsBlocked(false);
        actualDto.setIsSuspicious(false);
        actualDto.setBlockedReason("updatedBlockedReason");
        actualDto.setSuspiciousReason("updatedSuspiciousReason");

        SuspiciousPhoneTransferEntity result = mapper.mergeToEntity(actualDto, actualEntity);

        assertAll("Check entity properties",
                () -> assertThat(result).isSameAs(actualEntity),
                () -> assertThat(result.getPhoneTransferId()).isEqualTo(1L),
                () -> assertThat(result.getIsBlocked()).isFalse(),
                () -> assertThat(result.getIsSuspicious()).isFalse(),
                () -> assertThat(result.getBlockedReason()).isEqualTo("updatedBlockedReason"),
                () -> assertThat(result.getSuspiciousReason()).isEqualTo("updatedSuspiciousReason")
        );
    }

    @Test
    @DisplayName("слияние в entity, на вход подан null")
    void mergeToEntityNullTest() {
        SuspiciousPhoneTransferDto nullDto = null;
        SuspiciousPhoneTransferEntity actualEntity = new SuspiciousPhoneTransferEntity();
        actualEntity.setPhoneTransferId(1L);
        actualEntity.setIsBlocked(true);
        actualEntity.setIsSuspicious(true);
        actualEntity.setBlockedReason("blockedReason");
        actualEntity.setSuspiciousReason("suspiciousReason");

        SuspiciousPhoneTransferEntity result = mapper.mergeToEntity(nullDto, actualEntity);

        assertThat(result).isSameAs(actualEntity);
    }
}