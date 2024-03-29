package com.bank.profile.mapper;

import com.bank.profile.dto.AuditDto;
import com.bank.profile.entity.AuditEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ExtendWith(MockitoExtension.class)
@DisplayName("Тест класса AuditMapper")
class AuditMapperTest {

    final AuditEntity ENTITY = new AuditEntity(1L, "entityType", "operationType",
            "createdBy", "modifiedBy", new Timestamp(22L), new Timestamp(11L),
            "newEntityJson", "entityJson");

    final AuditMapper MAPPER = Mappers.getMapper(AuditMapper.class);

    @Test
    @DisplayName("маппинг в dto, на вход подан entity, позитивный тест")
    void toDtoPositiveTest() {
        AuditDto result = MAPPER.toDto(ENTITY);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals("entityType", result.getEntityType()),
                () -> assertEquals("operationType", result.getOperationType()),
                () -> assertEquals("createdBy", result.getCreatedBy()),
                () -> assertEquals("modifiedBy", result.getModifiedBy()),
                () -> assertEquals("entityJson", result.getEntityJson()),
                () -> assertEquals("entityJson", result.getEntityJson())
        );
    }

    @Test
    @DisplayName("маппинг в dto, на вход подан null, негативный тест")
    void toDtoNegativeTest() {
        AuditDto result = MAPPER.toDto(null);

        assertNull(result);
    }
}