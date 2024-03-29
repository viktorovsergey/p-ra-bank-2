package com.bank.transfer.service.Impl;

import com.bank.transfer.dto.CardTransferDto;
import com.bank.transfer.entity.CardTransferEntity;
import com.bank.transfer.mapper.CardTransferMapper;
import com.bank.transfer.repository.CardTransferRepository;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ExtendWith(MockitoExtension.class)
class CardTransferServiceImplTest {
    @Mock
    private CardTransferRepository repository;
    @Mock
    private CardTransferMapper mapper;
    @Mock
    private EntityNotFoundReturner notFoundReturner;
    @InjectMocks
    private CardTransferServiceImpl service;

    @Test
    @DisplayName("Поиск cardTransferEntity по id. Позитивный сценарий")
    void findByIdPositiveTest() {
        Long id = 1L;
        CardTransferEntity entity = new CardTransferEntity();
        CardTransferDto expectedDto = new CardTransferDto();

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(expectedDto);

        CardTransferDto result = service.findById(id);

        assertEquals(expectedDto, result);
    }

    @Test
    @DisplayName("Поиск card по id. Негативный сценарий")
    void findByIdNegativeTest() {
        Long id = 1L;

        when(repository.findById(id)).thenReturn(Optional.empty());
        when(notFoundReturner.getEntityNotFoundException(anyLong(), anyString())).thenReturn(
                new EntityNotFoundException());

        assertThrows(RuntimeException.class, () -> service.findById(id));
    }

    @Test
    @DisplayName("Поиск всех cardTransferEntity по id. Позитивный сценарий")
    void findAllByIdPositiveTest() {
        Long id1 = 1L, id2 = 2L;
        CardTransferEntity entity1 = new CardTransferEntity();
        CardTransferEntity entity2 = new CardTransferEntity();
        CardTransferDto dto1 = new CardTransferDto();
        CardTransferDto dto2 = new CardTransferDto();

        when(repository.findById(1L)).thenReturn(Optional.of(entity1));
        when(repository.findById(2L)).thenReturn(Optional.of(entity2));
        when(mapper.toDto(entity1)).thenReturn(dto1);
        when(mapper.toDto(entity2)).thenReturn(dto2);

        List<CardTransferDto> result = service.findAllById(Arrays.asList(id1, id2));

        assertEquals(2, result.size());
        assertEquals(dto1, result.get(0));
        assertEquals(dto2, result.get(1));
    }

    @Test
    @DisplayName("Поиск всех cardTransferEntity по id. Негативный сценарий")
    void testFindAllByIdNegativeTest() {
        List<Long> emptyIdList = Collections.emptyList();

        List<CardTransferDto> result = service.findAllById(emptyIdList);

        assertEquals(0, result.size());
        verifyNoInteractions(repository);
        verifyNoInteractions(mapper);
        verifyNoInteractions(notFoundReturner);
    }

}