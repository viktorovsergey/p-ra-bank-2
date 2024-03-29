package com.bank.history.service;

import com.bank.history.dto.HistoryDto;
import com.bank.history.entity.HistoryEntity;
import com.bank.history.mapper.HistoryMapper;
import com.bank.history.repository.HistoryRepository;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@DisplayName("Тест класса HistoryServiceImpl")
class HistoryServiceImplTest {
    final HistoryDto historyDto = new HistoryDto();
    final HistoryEntity historyEntity = new HistoryEntity();
    final List<Long> ids = new ArrayList<>();
    final List<HistoryEntity> historyEntityList = new ArrayList<>();
    @InjectMocks
    HistoryServiceImpl historyService;
    @Mock
    HistoryMapper mapper;
    @Mock
    HistoryRepository repository;

    @Test
    @DisplayName("чтение по существующему id, позитивный тест")
    void readByIdPositiveTest() {
        when(repository.findById(1L)).thenReturn(Optional.of(historyEntity));
        when(mapper.toDto(historyEntity)).thenReturn(historyDto);

        HistoryDto actualDto = historyService.readById(1L);

        verify(repository).findById(1L);
        verify(mapper).toDto(historyEntity);
        assertThat(actualDto).isEqualTo(historyDto);
    }

    @Test
    @DisplayName("Поиск по несуществующему id")
    void readByIdNegativeTest() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> historyService.readById(1L));
        verify(repository).findById(1L);
    }


    @Test
    @DisplayName("чтение списка dto по списку id, позитивный тест")
    void readAllByIdPositiveTest() {
        ids.add(1L);
        ids.add(2L);
        historyEntityList.add(historyEntity);
        historyEntityList.add(historyEntity);
        when(repository.findAllById(ids)).thenReturn(historyEntityList);
        when(mapper.toListDto(historyEntityList)).thenReturn(new ArrayList<HistoryDto>());

        List<HistoryDto> actualDtoList = historyService.readAllById(ids);

        verify(repository).findAllById(ids);
        verify(mapper).toListDto(historyEntityList);
        assertThat(actualDtoList).isEqualTo(new ArrayList<HistoryDto>());
    }

    @Test
    @DisplayName("чтение списка dto по списку id, негативный тест")
    void readAllByIdNegativeTest() {
        ids.add(1L);
        ids.add(2L);
        historyEntityList.add(historyEntity);

        when(repository.findAllById(ids)).thenReturn(historyEntityList);

        assertThrows(EntityNotFoundException.class, () -> historyService.readAllById(ids));
        verify(repository).findAllById(ids);
    }

    @Test
    @DisplayName("создание, позитивный тест ")
    void createPositiveTest() {
        when(mapper.toEntity(historyDto)).thenReturn(historyEntity);
        when(repository.save(historyEntity)).thenReturn(historyEntity);
        when(mapper.toDto(historyEntity)).thenReturn(historyDto);

        HistoryDto actualDto = historyService.create(historyDto);

        verify(mapper).toEntity(historyDto);
        verify(repository).save(historyEntity);
        verify(mapper).toDto(historyEntity);
        assertThat(actualDto).isEqualTo(historyDto);
    }

    @Test
    @DisplayName("обновление существующих данных, позитивный тест")
    void updatePositiveTest() {
        historyDto.setId(1L);
        historyEntity.setId(1L);
        HistoryEntity updatedHistoryEntity = new HistoryEntity();
        updatedHistoryEntity.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(historyEntity));
        when(mapper.mergeToEntity(any(), any())).thenReturn(updatedHistoryEntity);
        when(repository.save(any())).thenReturn(updatedHistoryEntity);
        when(mapper.toDto(updatedHistoryEntity)).thenReturn(historyDto);

        HistoryDto updatedDto = historyService.update(1L, historyDto);

        assertNotNull(updatedDto);
        assertThat(updatedDto.getId()).isEqualTo(historyDto.getId());
    }

    @Test
    @DisplayName("обновление существующих данных, негитивный тест")
    void updateNegativeTest() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        HistoryDto actualDto = new HistoryDto();

        assertThrows(EntityNotFoundException.class, () -> historyService.update(1L, actualDto));
    }


}