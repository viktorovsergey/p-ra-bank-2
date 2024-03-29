package com.bank.transfer.service.Impl;

import com.bank.transfer.dto.AuditDto;
import com.bank.transfer.entity.AuditEntity;
import com.bank.transfer.mapper.AuditMapper;
import com.bank.transfer.repository.AuditRepository;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ExtendWith(MockitoExtension.class)
class AuditServiceImplTest {
    @Mock
    private AuditRepository repository;
    @Mock
    private AuditMapper mapper;
    @InjectMocks
    private AuditServiceImpl auditService;

    @Test
    @DisplayName("Поиск auditEntity по id. Позитивный сценарий")
    void findByIdPositiveTest() {
        Long auditId = 1L;
        AuditEntity auditEntity = new AuditEntity();
        auditEntity.setId(auditId);
        AuditDto expectedDto = new AuditDto();
        expectedDto.setId(auditId);

        when(repository.findById(auditId)).thenReturn(Optional.of(auditEntity));
        when(mapper.toDto(auditEntity)).thenReturn(expectedDto);

        AuditDto actualDto = auditService.findById(auditId);

        assertEquals(expectedDto, actualDto);
        verify(repository, times(1)).findById(auditId);
        verify(mapper, times(1)).toDto(auditEntity);
    }

    @Test
    @DisplayName("Поиск auditEntity по id. Негативный сценарий")
    void findByIdNegativeTest() {

        Long nonExistingAuditId = 2L;

        when(repository.findById(nonExistingAuditId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> auditService.findById(nonExistingAuditId));
        verify(repository, times(1)).findById(nonExistingAuditId);
        verifyNoInteractions(mapper);
    }
}