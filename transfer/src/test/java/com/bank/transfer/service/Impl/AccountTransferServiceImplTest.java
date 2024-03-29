package com.bank.transfer.service.Impl;

import com.bank.transfer.dto.AccountTransferDto;
import com.bank.transfer.entity.AccountTransferEntity;
import com.bank.transfer.mapper.AccountTransferMapper;
import com.bank.transfer.repository.AccountTransferRepository;
import com.bank.transfer.service.common.EntityNotFoundReturner;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ExtendWith(MockitoExtension.class)
class AccountTransferServiceImplTest {
    @Mock
    private AccountTransferRepository repository;
    @Mock
    private AccountTransferMapper mapper;
    @InjectMocks
    private AccountTransferServiceImpl service;
    @Mock
    EntityNotFoundReturner entityNotFoundReturner;
    @InjectMocks
    AccountTransferServiceImpl accountTransferService;

    @Test
    @DisplayName("Поиск всех accountTransfer по ids. Позитивный сценарий")
    void findAllByIdPositiveTest() {
        List<Long> ids = Arrays.asList(1L, 2L, 3L);
        List<AccountTransferEntity> entities =
                Arrays.asList(new AccountTransferEntity(1L, 1L,
                                BigDecimal.valueOf(1), "1", 1L),
                        new AccountTransferEntity(2L, 2L,
                                BigDecimal.valueOf(2), "2", 2L),
                        new AccountTransferEntity(3L, 3L,
                                BigDecimal.valueOf(3), "3", 3L));

        Mockito.when(repository.findById(anyLong())).thenAnswer(invocation -> {
            Long id = invocation.getArgument(0);
            int index = ids.indexOf(id);
            return index != -1 ? Optional.of(entities.get(index)) : Optional.empty();
        });

        Mockito.when(mapper.toDto(any())).thenReturn(new AccountTransferDto());

        List<AccountTransferDto> resultAccountTransferDto = accountTransferService.findAllById(ids);

        assertEquals(ids.size(), resultAccountTransferDto.size());
        verify(mapper, times(ids.size())).toDto(any());
    }

    @Test
    @DisplayName("Поиск accountTransfer по id. Позитивный сценарий")
    void findAllByIdNegativeTest() {
        List<Long> ids = Collections.singletonList(-1L);
        Mockito.when(repository.findById(-1L)).thenReturn(Optional.empty());
        assertThrows(NullPointerException.class, () -> accountTransferService.findAllById(ids));
    }

    @Test
    @DisplayName("Поиск accountTransfer по id. Позитивный сценарий")
    void findByIdPositiveTest() {
        Long id = 1L;
        AccountTransferEntity entity =
                new AccountTransferEntity(1L, 1L, BigDecimal.valueOf(1),
                        "1", 1L);

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(new AccountTransferDto());

        AccountTransferDto expectedDto = new AccountTransferDto();
        AccountTransferDto actualDto = service.findById(id);

        assertEquals(expectedDto.getClass(), actualDto.getClass());
        verify(repository, times(1)).findById(any());
    }

    @Test
    @DisplayName("Поиск accountTransfer по id. Негативный сценарий")
    void findByIdNegativeTest() {
        Long id = -1L;

        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());
        Mockito.when(entityNotFoundReturner.getEntityNotFoundException(eq(id), anyString()))
                .thenReturn(new EntityNotFoundException());

        assertThatThrownBy(() -> accountTransferService.findById(id)).isInstanceOf(
                EntityNotFoundException.class);
    }

    @Test
    @DisplayName("Сохранение accountTransfer. Позитивный сценарий")
    void savePositiveTest() {
        AccountTransferDto inputDto =
                new AccountTransferDto(1L, 1L, BigDecimal.valueOf(1),
                        "1", 1L);
        AccountTransferEntity expectedEntity =
                new AccountTransferEntity(1L, 1L, BigDecimal.valueOf(1),
                        "1", 1L);

        when(mapper.toEntity(inputDto)).thenReturn(expectedEntity);
        when(repository.save(expectedEntity)).thenReturn(expectedEntity);
        when(mapper.toDto(expectedEntity)).thenReturn(inputDto);

        AccountTransferDto actualDto = service.save(inputDto);

        assertEquals(inputDto, actualDto);
        verify(repository, times(1)).save(any());
    }

    @Test
    @DisplayName("Сохранение записи accountTransfer. Негативный сценарий")
    void saveNegativeTest() {
        Mockito.when(repository.save(any())).thenReturn(Optional.empty());
        assertThrows(ClassCastException.class, () -> accountTransferService.save(any()));
    }

    @Test
    @DisplayName("Обновление записи accountTransfer. Позитивный сценарий")
    void updatePositiveTest() {
        long id = 1L;
        AccountTransferDto inputDto =
                new AccountTransferDto(1L, 1L, BigDecimal.valueOf(1),
                        "1", 1L);
        AccountTransferEntity existingEntity =
                new AccountTransferEntity(1L, 1L, BigDecimal.valueOf(1),
                        "1", 1L);

        when(repository.findById(id)).thenReturn(Optional.of(existingEntity));
        when(mapper.mergeToEntity(inputDto, existingEntity)).thenReturn(existingEntity);
        when(repository.save(existingEntity)).thenReturn(existingEntity);
        when(mapper.toDto(existingEntity)).thenReturn(new AccountTransferDto());

        AccountTransferDto resultDto = accountTransferService.update(id, inputDto);

        assertNotNull(resultDto);
        verify(repository).findById(id);
        verify(mapper).mergeToEntity(inputDto, existingEntity);
        verify(repository).save(existingEntity);
        verify(mapper).toDto(existingEntity);
    }

    @Test
    @DisplayName("Обновление записи accountTransfer. Негативный сценарий")
    void updateNegativeTest() {
        Long id = 1L;

        Mockito.when(repository.findById(any())).thenReturn(Optional.empty());
        Mockito.when(entityNotFoundReturner.getEntityNotFoundException(eq(id), anyString()))
                .thenReturn(new EntityNotFoundException());

        assertThrows(EntityNotFoundException.class, () -> accountTransferService.update(id, any()));

        verify(repository).findById(id);
    }

}