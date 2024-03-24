package com.bank.account.service;

import com.bank.account.dto.AuditDto;
import com.bank.account.entity.AuditEntity;
import com.bank.account.mapper.AccountAuditMapper;
import com.bank.account.repository.AccountAuditRepository;
import com.bank.account.service.common.ExceptionReturner;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@DisplayName("Тест класса AccountAuditService")
@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountAuditServiceTest {

    final AuditDto ACCOUNT_AUDIT_DTO = new AuditDto(1L, "account", "create", "user",
            "user", null, null,"abc","1234");
    final AuditEntity ACCOUNT_AUDIT_ENTITY = new AuditEntity(1L, "account", "create", "user",
            "user", null, null,"abc","1234");

    @Mock
    AccountAuditRepository repository;
    @Mock
    AccountAuditMapper mapper;
    @Mock
    ExceptionReturner exceptionReturner;
    @InjectMocks
    AccountAuditServiceImpl accountAuditService;

    @Test
    @DisplayName("поиск по существующему id, позитивный тест")
    void findByIdPositiveTest() {
        doReturn(Optional.of(ACCOUNT_AUDIT_ENTITY)).when(repository).findById(1L);
        doReturn(ACCOUNT_AUDIT_DTO).when(mapper).toDto(ACCOUNT_AUDIT_ENTITY);
        AuditDto result = accountAuditService.findById(1L);
        assertNotNull(result);
        assertEquals(ACCOUNT_AUDIT_DTO, result);
    }

    @Test
    @DisplayName("поиск по несуществующему id, негативный тест")
    void findByIdNegativeTest() {
        doReturn(Optional.empty()).when(repository).findById(ACCOUNT_AUDIT_ENTITY.getId());
        doReturn(new EntityNotFoundException()).when(this.exceptionReturner).getEntityNotFoundException("Не существующий id = " + ACCOUNT_AUDIT_ENTITY.getId());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () ->
                accountAuditService.findById(ACCOUNT_AUDIT_ENTITY.getId()));

    }
}