package com.bank.account.mapper;

import com.bank.account.dto.AuditDto;
import com.bank.account.entity.AuditEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Тест класса AccountAuditMapperTest")
@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountAuditMapperTest {

    final AccountAuditMapper MAPPER = Mappers.getMapper(AccountAuditMapper.class);
    final AuditDto ACCOUNT_AUDIT_DTO = new AuditDto(1L, "account", "create", "user", "user", null, null,"abc","1234");
    final AuditEntity ACCOUNT_AUDIT_ENTITY = new AuditEntity(1L, "account", "create", "user", "user", null, null,"abc","1234");

    @Test
    @DisplayName("маппинг в ДТО, на вход подан entity, позитивный тест")
    void toDtoTest() {
        final AuditDto result = MAPPER.toDto(ACCOUNT_AUDIT_ENTITY);
        assertNotNull(result);
        assertThat(result.getId()).isEqualTo(ACCOUNT_AUDIT_DTO.getId());
        assertNotNull(result.getId());
    }

    @Test
    @DisplayName("маппинг в ДТО, на вход подан null, негативный тест")
    void toDtoNullTest() {
        final AuditDto result = MAPPER.toDto(null);
        assertNull(result);
    }
}
