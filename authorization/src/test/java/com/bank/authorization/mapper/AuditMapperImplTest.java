package com.bank.authorization.mapper;

import com.bank.authorization.dto.AuditDto;
import com.bank.authorization.entity.AuditEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.sql.Timestamp;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class AuditMapperImplTest {

    AuditMapper mapper = Mappers.getMapper(AuditMapper.class);

    AuditEntity getAuditEntity() {
        return new AuditEntity(
            1L,
            "entityType",
            "operationType",
            "createdBy",
            "modifiedBy",
            new Timestamp(10L),
            new Timestamp(20L),
            "newEntityJson",
            "entityJson"
            );
    }

    AuditDto getAuditDto() {
        return new AuditDto(
                1L,
                "entityType",
                "operationType",
                "createdBy",
                "modifiedBy",
                new Timestamp(10L),
                new Timestamp(20L),
                "newEntityJson",
                "entityJson"
        );
    }

    @Test
    @DisplayName("Маппинг в DTO")
    void toDtoTest() {
        AuditEntity auditEntity = getAuditEntity();
        AuditDto expectedDto = getAuditDto();

        AuditDto actualDto = mapper.toDto(auditEntity);

        assertNotNull(actualDto);
        assertEquals(actualDto, expectedDto);

    }

    @Test
    @DisplayName("Маппинг в DTO, на вход подается null")
    void toDtoNullTest() {
        AuditEntity auditEntity = null;

        AuditDto actualDto = mapper.toDto(auditEntity);

        assertNull(actualDto);
    }

}