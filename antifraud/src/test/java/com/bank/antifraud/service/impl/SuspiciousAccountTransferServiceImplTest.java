package com.bank.antifraud.service.impl;

import com.bank.antifraud.dto.SuspiciousAccountTransferDto;
import com.bank.antifraud.entity.SuspiciousAccountTransferEntity;
import com.bank.antifraud.mappers.SuspiciousAccountTransferMapper;
import com.bank.antifraud.repository.SuspiciousAccountTransferRepository;
import com.bank.antifraud.service.common.ExceptionReturner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@ExtendWith(MockitoExtension.class)
class SuspiciousAccountTransferServiceImplTest {

    @Mock
    SuspiciousAccountTransferRepository repository;
    @Mock
    SuspiciousAccountTransferMapper mapper;
    @Mock
    ExceptionReturner returner;
    @InjectMocks
    SuspiciousAccountTransferServiceImpl service;

    @Test
    @DisplayName("сохранение entity с корректными данными, позитивный сценарий")
    void savePositiveTest() {
        SuspiciousAccountTransferEntity entity = createEntity();
        SuspiciousAccountTransferDto dto = createDto();

        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toEntity(dto)).thenReturn(entity);
        when(mapper.toDto(entity)).thenReturn(dto);

        SuspiciousAccountTransferDto result = service.save(dto);

        assertThat(result).isEqualTo(dto);
        verify(repository, times(1)).save(entity);
        verify(mapper, times(1)).toEntity(dto);
        verify(mapper, times(1)).toDto(entity);
    }

    @Test
    @DisplayName("сохранение entity с некорректными данными, негативный сценарий")
    void saveNegativeTest() {
        SuspiciousAccountTransferEntity entity = createEntity();
        SuspiciousAccountTransferDto dto = createDto();

        when(repository.save(entity)).thenThrow(new RuntimeException("Failed to save entity"));
        when(mapper.toEntity(dto)).thenReturn(entity);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.save(dto));

        assertThat(exception.getMessage()).isEqualTo("Failed to save entity");
    }

    @Test
    @DisplayName("поиск по id, позитивный сценарий")
    void findByIdPositiveTest() {
        SuspiciousAccountTransferEntity entity = createEntity();
        SuspiciousAccountTransferDto dto = createDto();

        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.toDto(entity)).thenReturn(dto);

        SuspiciousAccountTransferDto result = service.findById(1L);

        assertThat(result).isEqualTo(dto);
        verify(repository, times(1)).findById(1L);
        verify(mapper, times(1)).toDto(entity);
    }

    @Test
    @DisplayName("поиск по несуществующему id, негативный сценарий")
    void findByNonExistIdNegativeTest() {

        when(repository.findById(1L)).thenReturn(Optional.empty());
        when(returner.getEntityNotFoundException("SuspiciousAccountTransfer по данному id не существует"))
                .thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> service.findById(1L));
        verify(repository, times(1)).findById(1L);
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("обновление entity с корректным id, позитивный сценарий")
    void updateEntityIfEntityIsExistPositiveTest() {
        SuspiciousAccountTransferEntity entity = createEntity();
        SuspiciousAccountTransferDto dto = createDto();

        SuspiciousAccountTransferEntity updatedEntity = createEntity();
        updatedEntity.setIsSuspicious(false);
        SuspiciousAccountTransferDto updatedDto = createDto();
        updatedDto.setIsSuspicious(false);


        when(repository.findById(1L)).thenReturn(Optional.of(entity));
        when(mapper.mergeToEntity(dto, entity)).thenReturn(updatedEntity);
        when(repository.save(updatedEntity)).thenReturn(updatedEntity);
        when(mapper.toDto(updatedEntity)).thenReturn(updatedDto);

        SuspiciousAccountTransferDto result = service.update(1L, dto);

        assertThat(result).isEqualTo(updatedDto);
        verify(repository, times(1)).findById(1L);
        verify(mapper, times(1)).mergeToEntity(dto, entity);
        verify(repository, times(1)).save(updatedEntity);
        verify(mapper, times(1)).toDto(updatedEntity);
    }

    @Test
    @DisplayName("обновление entity с некорректным id, негативный сценарий")
    void updateNonExistEntityNegativeTest() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        when(returner.getEntityNotFoundException("SuspiciousAccountTransfer по данному id не существует"))
                .thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> service.findById(1L));
        verify(repository, times(1)).findById(1L);
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("поиск коллекции с корректными id, позитивный сценарий")
    void findAllByIdIfEntitiesAreExistPositiveTest() {
        List<SuspiciousAccountTransferEntity> entityList = createEntityList();
        List<SuspiciousAccountTransferDto> dtoList = createDtoList();

        when(repository.findById(1L)).thenReturn(Optional.of(new SuspiciousAccountTransferEntity(1L, 1L,
                true, true, "blockedReason", "suspiciousReason")));
        when(repository.findById(2L)).thenReturn(Optional.of(new SuspiciousAccountTransferEntity(2L, 2L,
                true, true, "blockedReason", "suspiciousReason")));
        when(mapper.toListDto(entityList)).thenReturn(dtoList);

        List<SuspiciousAccountTransferDto> result = service.findAllById(List.of(1L, 2L));

        assertThat(result).isEqualTo(dtoList);
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).findById(2L);
        verify(mapper, times(1)).toListDto(entityList);
    }

    @Test
    @DisplayName("поиск коллекции с некорректными id, негативный сценарий")
    void findAllByIdNonExistNegativeTest() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        when(returner.getEntityNotFoundException("SuspiciousAccountTransfer по данному id не существует"))
                .thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> service.findById(1L));
        verify(repository, times(1)).findById(1L);
        verifyNoInteractions(mapper);
    }

    private static SuspiciousAccountTransferEntity createEntity() {
        return new SuspiciousAccountTransferEntity(1L, 1L, true, true,
                "blockedReason", "suspiciousReason");
    }

    private static SuspiciousAccountTransferDto createDto() {
        return new SuspiciousAccountTransferDto(1L, 1L, true, true,
                "blockedReason", "suspiciousReason");
    }

    private static List<SuspiciousAccountTransferEntity> createEntityList() {
        return List.of(new SuspiciousAccountTransferEntity(1L, 1L, true, true,
                        "blockedReason", "suspiciousReason"),
                new SuspiciousAccountTransferEntity(2L, 2L, true, true,
                        "blockedReason", "suspiciousReason"));
    }

    private static List<SuspiciousAccountTransferDto> createDtoList() {
        return List.of(new SuspiciousAccountTransferDto(1L, 1L, true, true,
                        "blockedReason", "suspiciousReason"),
                new SuspiciousAccountTransferDto(2L, 2L, true, true,
                        "blockedReason", "suspiciousReason"));
    }
}