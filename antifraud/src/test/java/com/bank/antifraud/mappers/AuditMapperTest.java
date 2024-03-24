package com.bank.antifraud.mappers;

import com.bank.antifraud.dto.AuditDto;
import com.bank.antifraud.entity.AuditEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class AuditMapperTest {
    AuditMapperImpl mapper = new AuditMapperImpl();

    @Test
    @DisplayName("маппинг в dto")
    void toDtoTest() {
        AuditEntity actualEntity = new AuditEntity();
        actualEntity.setId(1L);
        actualEntity.setEntityType("entityType1");
        actualEntity.setOperationType("operationType1");
        actualEntity.setCreatedBy("createdBy1");
        actualEntity.setModifiedBy("modifiedBy1");
        actualEntity.setCreatedAt(new Timestamp(1));
        actualEntity.setModifiedAt(new Timestamp(1000));
        actualEntity.setNewEntityJson("newEntityJson1");
        actualEntity.setEntityJson("entityJson1");


        AuditDto result = mapper.toDto(actualEntity);

        assertAll(
                () -> assertThat(result.getId()).isEqualTo(1L),
                () -> assertThat(result.getEntityType()).isEqualTo("entityType1"),
                () -> assertThat(result.getOperationType()).isEqualTo("operationType1"),
                () -> assertThat(result.getCreatedBy()).isEqualTo("createdBy1"),
                () -> assertThat(result.getModifiedBy()).isEqualTo("modifiedBy1"),
                () -> assertThat(result.getCreatedAt()).isEqualTo(new Timestamp(1)),
                () -> assertThat(result.getModifiedAt()).isEqualTo(new Timestamp(1000)),
                () -> assertThat(result.getNewEntityJson()).isEqualTo("newEntityJson1"),
                () -> assertThat(result.getEntityJson()).isEqualTo("entityJson1")
        );
    }

    @Test
    @DisplayName("маппинг в dto, на вход подан null")
    void noDtoNullTest() {
        AuditEntity entity = null;

        AuditDto result = mapper.toDto(entity);

        assertThat(result).isNull();
    }
}