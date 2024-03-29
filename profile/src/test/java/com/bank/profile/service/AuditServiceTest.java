package com.bank.profile.service;

import com.bank.profile.dto.AuditDto;
import com.bank.profile.entity.AuditEntity;
import com.bank.profile.mapper.AuditMapper;
import com.bank.profile.repository.AuditRepository;
import com.bank.profile.service.impl.AuditServiceImpl;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@FieldDefaults(level = AccessLevel.PRIVATE)
@ExtendWith(MockitoExtension.class)
@DisplayName("Тест класса AuditService")
class AuditServiceTest {
    final AuditEntity ENTITY = new AuditEntity(1L, "entityType", "operationType",
            "createdBy", "modifiedBy", new Timestamp(22L), new Timestamp(11L),
            "newEntityJson", "entityJson");
    final AuditDto DTO = new AuditDto(1L, "entityType", "operationType",
            "createdBy", "modifiedBy", new Timestamp(22L), new Timestamp(11L),
            "newEntityJson", "entityJson");
    @Mock
    AuditRepository repository;

    @Mock
    AuditMapper mapper;

    @InjectMocks
    AuditServiceImpl AuditServiceService;

    @Test
    @DisplayName("поиск по существующему id, позитивный тест")
    void findByIdPositiveTest() {
        when(repository.findById(ENTITY.getId())).thenReturn(Optional.of(ENTITY));
        when(mapper.toDto(ENTITY)).thenReturn(DTO);

        AuditDto result = AuditServiceService.findById(ENTITY.getId());

        assertNotNull(result);
        assertEquals(ENTITY.getId(), result.getId());
        verify(repository).findById(ENTITY.getId());
        verify(mapper).toDto(ENTITY);
    }

    @Test
    @DisplayName("поиск по несуществующему id, негативный тест")
    void findByIdNegativeTest() {
        when(repository.findById(ENTITY.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> AuditServiceService.findById(ENTITY.getId()));
        verify(repository).findById(ENTITY.getId());
    }
}