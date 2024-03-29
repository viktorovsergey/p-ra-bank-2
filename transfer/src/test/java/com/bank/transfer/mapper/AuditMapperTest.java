package com.bank.transfer.mapper;

import com.bank.transfer.dto.AuditDto;
import com.bank.transfer.entity.AuditEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class AuditMapperTest {
    final AuditMapper auditMapper = Mappers.getMapper(AuditMapper.class);
    final Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    final Timestamp timestamp1 = new Timestamp(System.currentTimeMillis() + 100000);
    final AuditDto auditDto = new AuditDto(1L, "1", "1",
            "1", "1", timestamp, timestamp1,
            "{newEntityJson}", "{entityJson}");
    final AuditEntity auditEntity = new AuditEntity(1L, "1", "1",
            "1", "1", timestamp, timestamp1,
            "{newEntityJson}", "{entityJson}");

    @Test
    @DisplayName("Маппинг auditEntity в dto")
    void toDtoTest() {

        AuditDto auditDto = auditMapper.toDto(auditEntity);

        assertAll(
                () -> assertEquals(this.auditDto.getId(), auditDto.getId()),
                () -> assertEquals(this.auditDto.getEntityType(), auditDto.getEntityType()),
                () -> assertEquals(this.auditDto.getOperationType(), auditDto.getOperationType()),
                () -> assertEquals(this.auditDto.getCreatedBy(), auditDto.getCreatedBy()),
                () -> assertEquals(this.auditDto.getModifiedBy(), auditDto.getModifiedBy()),
                () -> assertEquals(this.auditDto.getCreatedAt(), auditDto.getCreatedAt()),
                () -> assertEquals(this.auditDto.getModifiedAt(), auditDto.getModifiedAt()),
                () -> assertEquals(this.auditDto.getNewEntityJson(), auditDto.getNewEntityJson()),
                () -> assertEquals(this.auditDto.getEntityJson(), auditDto.getEntityJson())
        );
    }

    @Test
    @DisplayName("Маппинг auditEntity в dto, на вход подан null")
    void toDtoNullTest() {
        AuditDto auditDtoNull = auditMapper.toDto(null);

        assertNull(auditDtoNull);
    }
}