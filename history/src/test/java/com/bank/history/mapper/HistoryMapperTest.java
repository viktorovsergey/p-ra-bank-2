package com.bank.history.mapper;

import com.bank.history.dto.HistoryDto;
import com.bank.history.entity.HistoryEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@FieldDefaults(level = AccessLevel.PRIVATE)
@DisplayName("Тест класса HistoryMapper")
class HistoryMapperTest {

    final HistoryMapper MAPPER = Mappers.getMapper(HistoryMapper.class);
    final HistoryDto historyDto = new HistoryDto(1L, 2L, 2L, 2L, 2L, 2L, 2L);
    final HistoryEntity historyEntity = new HistoryEntity(1L, 2L, 2L, 2L, 2L, 2L, 2L);

    @Test
    @DisplayName("преобразование DTO в Entity, позитивный тест")
    void toEntityPositiveTest() {
        HistoryEntity actualEntity = MAPPER.toEntity(historyDto);

        assertAll(
                () -> assertNotNull(actualEntity),
                () -> assertThat(actualEntity.getTransferAuditId())
                        .isEqualTo(historyDto.getTransferAuditId())
        );
    }

    @Test
    @DisplayName("преобразование DTO в Entity, на вход подан null, негативный тест")
    void toEntityNegativeTest() {
        HistoryEntity actualEntity = MAPPER.toEntity(null);

        assertNull(actualEntity);
    }

    @Test
    @DisplayName("преобразование Entity в DTO , позитивный тест")
    void toDtoPositiveTest() {
        HistoryDto actualDto = MAPPER.toDto(historyEntity);

        assertAll(
                () -> assertNotNull(actualDto),
                () -> assertThat(actualDto.getId()).isEqualTo(historyEntity.getId()),
                () -> assertThat(actualDto.getTransferAuditId())
                        .isEqualTo(historyEntity.getTransferAuditId())
        );
    }

    @Test
    @DisplayName("преобразование Entity в DTO на вход подан null, негативный тест")
    void toDtoNegativeTest() {
        HistoryDto actualDto = MAPPER.toDto(null);

        assertNull(actualDto);
    }

    @Test
    @DisplayName("слияние в entity, на вход подан dto и entity, позитивный тест")
    void mergeToEntityPositiveTest() {
        HistoryEntity actualEntity = MAPPER.mergeToEntity(historyDto, historyEntity);

        assertAll(
                () -> assertNotNull(actualEntity.getId()),
                () -> assertEquals(historyEntity.getId(), actualEntity.getId()),
                () -> assertEquals(historyEntity.getTransferAuditId(), actualEntity.getTransferAuditId())
        );
    }

    @Test
    @DisplayName("слияние в entity, на вход подан null, негативный тест")
    void mergeToEntityNegativeTest() {
        HistoryEntity actualEntity = MAPPER.mergeToEntity(null, null);

        assertNull(actualEntity);
    }

    @Test
    @DisplayName("маппинг в List<dto>, на вход подан List<entity>, позитивный тест")
    void toDtoListPositiveTest() {
        List<HistoryEntity> entityList = new ArrayList<>();
        entityList.add(historyEntity);

        List<HistoryDto> result = MAPPER.toListDto(entityList);

        assertAll(
                () -> assertNotNull(result.get(0).getId()),
                () -> assertEquals(historyEntity.getId(), result.get(0).getId()),
                () -> assertEquals(historyEntity.getTransferAuditId(), result.get(0).getTransferAuditId())
        );
    }

    @Test
    @DisplayName("маппинг в List<dto>, на вход подан null, негативный тест")
    void toDtoListNegativeTest() {
        List<HistoryDto> result = MAPPER.toListDto(null);

        assertNull(result);
    }
}