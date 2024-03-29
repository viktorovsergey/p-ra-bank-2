package com.bank.transfer.service.Impl;

import com.bank.transfer.dto.PhoneTransferDto;
import com.bank.transfer.entity.PhoneTransferEntity;
import com.bank.transfer.mapper.PhoneTransferMapper;
import com.bank.transfer.repository.PhoneTransferRepository;
import com.bank.transfer.service.common.EntityNotFoundReturner;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ExtendWith(MockitoExtension.class)
public class PhoneTransferServiceImplTest {
    @Mock
    private PhoneTransferRepository repository;
    @Mock
    private PhoneTransferMapper mapper;
    @Mock
    private EntityNotFoundReturner notFoundReturner;
    @InjectMocks
    private PhoneTransferServiceImpl service;

    @Test
    @DisplayName("Поиск phoneTransferEntity по ids. Позитивный сценарий")
    public void findAllByIdPositiveTest() {
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        when(repository.findById(1L)).thenReturn(Optional.of(new PhoneTransferEntity()));
        when(repository.findById(2L)).thenReturn(Optional.of(new PhoneTransferEntity()));

        List<PhoneTransferDto> result = service.findAllById(ids);

        assertEquals(2, result.size());
    }


    @Test
    @DisplayName("Поиск всех phoneTransferEntity по ids. Негативный сценарий")
    public void findAllByIdNegativeTest() {
        List<Long> emptyIds = new ArrayList<>();
        List<Long> nonEmptyIds = new ArrayList<>();
        nonEmptyIds.add(1L);
        nonEmptyIds.add(2L);

        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        when(notFoundReturner.getEntityNotFoundException(anyLong(), anyString())).thenReturn(
                new EntityNotFoundException(""));

        List<PhoneTransferDto> emptyResult = service.findAllById(emptyIds);
        assertTrue(emptyResult.isEmpty());

        assertThrows(EntityNotFoundException.class, () -> service.findAllById(nonEmptyIds));
    }

    @Test
    @DisplayName("Поиск phoneTransfer по ids. Позитивный сценарий")
    public void findByIdPositiveTest() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.of(new PhoneTransferEntity()));
        when(mapper.toDto(any())).thenReturn(new PhoneTransferDto());

        PhoneTransferDto result = service.findById(id);

        assertNotNull(result);
    }

    @Test
    @DisplayName("Поиск phoneTransfer по ids. Негативный сценарий")
    public void findByIdNegativeTest() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());
        when(notFoundReturner.getEntityNotFoundException(anyLong(), anyString())).thenReturn(
                new EntityNotFoundException(""));

        assertThrows(EntityNotFoundException.class, () -> service.findById(id));
    }

    @Test
    @DisplayName("Сохранение записи phoneTransfer. Позитивный сценарий")
    public void savePositiveTest() {
        PhoneTransferDto dto = new PhoneTransferDto();
        when(mapper.toEntity(dto)).thenReturn(new PhoneTransferEntity());
        when(repository.save(any())).thenReturn(new PhoneTransferEntity());
        when(mapper.toDto(any())).thenReturn(dto);

        PhoneTransferDto result = service.save(dto);

        assertNotNull(result);
    }

    @Test
    @DisplayName("Сохранение записи phoneTransfer. Негативный сценарий")
    public void saveSaveNegative() {
        PhoneTransferDto dto = new PhoneTransferDto();
        when(mapper.toEntity(dto)).thenThrow(new RuntimeException());

        assertThrows(RuntimeException.class, () -> service.save(dto));
    }

}