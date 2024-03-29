package com.bank.profile.service;

import com.bank.profile.dto.AccountDetailsIdDto;

import com.bank.profile.entity.AccountDetailsIdEntity;
import com.bank.profile.mapper.AccountDetailsIdMapper;
import com.bank.profile.repository.AccountDetailsIdRepository;
import com.bank.profile.service.impl.AccountDetailsIdServiceImp;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ExtendWith(MockitoExtension.class)
@DisplayName("Тест класса AccountDetailsIdService")
class AccountDetailsIdServiceTest {
    final AccountDetailsIdEntity ENTITY = new AccountDetailsIdEntity(1L, 1L, null);

    final AccountDetailsIdDto DTO = new AccountDetailsIdDto(1L, 1L, null);
    @Mock
    AccountDetailsIdRepository repository;

    @Mock
    AccountDetailsIdMapper mapper;

    @InjectMocks
    AccountDetailsIdServiceImp accountDetailsIdService;

    @Test
    @DisplayName("поиск по существующему id, позитивный тест")
    void findByIdPositiveTest() {
        when(repository.findById(1L)).thenReturn(Optional.of(ENTITY));
        when(mapper.toDto(ENTITY)).thenReturn(DTO);

        AccountDetailsIdDto result = accountDetailsIdService.findById(1L);

        assertNotNull(result);
        assertEquals(ENTITY.getId(), result.getId());
        verify(repository).findById(ENTITY.getId());
        verify(mapper).toDto(ENTITY);
    }

    @Test
    @DisplayName("поиск по несуществующему id, негативный тест")
    void findByIdNegativeTest() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> accountDetailsIdService.findById(2L));
        verify(repository).findById(2L);
    }

    @Test
    @DisplayName("поиск List<dto> по существующему списку, позитивный тест")
    public void findAllByIdPositiveTest() {
        List<Long> ids = List.of(1L);
        List<AccountDetailsIdEntity> entityList = List.of(ENTITY);
        List<AccountDetailsIdDto> dtoList = List.of(DTO);

        when(repository.findAllById(ids)).thenReturn(entityList);
        when(mapper.toDtoList(entityList)).thenReturn(dtoList);

        List <AccountDetailsIdDto> result = accountDetailsIdService.findAllById(ids);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(1, result.size()),
                () -> verify(mapper).toDtoList(entityList),
                () -> verify(repository).findAllById(any())
        );
    }

    @Test
    @DisplayName("поиск List<dto> по несуществующему списку, негативный тест")
    public void findAllByIdNegativeTest() {
        when(repository.findById(any())).thenThrow(new NullPointerException("Ошибка получения списка данных"));

        assertThrows(NullPointerException.class, () ->
                accountDetailsIdService.update(ENTITY.getId(), DTO));
    }

    @Test
    @DisplayName("сохранение корректных данных, позитивный тест")
    void savePositiveTest() {
        when(mapper.toEntity(DTO)).thenReturn(ENTITY);
        when(repository.save(ENTITY)).thenReturn(ENTITY);
        when(mapper.toDto(ENTITY)).thenReturn(DTO);

        AccountDetailsIdDto result = accountDetailsIdService.save(DTO);

        assertNotNull(result);
        verify(mapper).toEntity(DTO);
        verify(repository).save(ENTITY);
        verify(mapper).toDto(ENTITY);
    }

    @Test
    @DisplayName("сохранение некорректных данных, негативный тест")
    void saveNegativeTest() {
        when(mapper.toEntity(DTO)).thenReturn(ENTITY);
        when(repository.save(ENTITY)).thenThrow(new RuntimeException("Ошибка сохранения данных"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> accountDetailsIdService.save(DTO));

        assertEquals("Ошибка сохранения данных", exception.getMessage());
    }

    @Test
    @DisplayName("обновление существующих данных, позитивный тест")
    public void updatePositiveTest() {

        when(repository.findById(ENTITY.getId())).thenReturn(Optional.of(ENTITY));
        when(mapper.mergeToEntity(DTO, ENTITY)).thenReturn(ENTITY);
        when(repository.save(ENTITY)).thenReturn(ENTITY);
        when(mapper.toDto(ENTITY)).thenReturn(DTO);

        AccountDetailsIdDto result = accountDetailsIdService.update(ENTITY.getId(), DTO);

        assertNotNull(result);
        verify(repository).findById(ENTITY.getId());
        verify(mapper).mergeToEntity(DTO, ENTITY);
        verify(repository).save(ENTITY);
        verify(mapper).toDto(ENTITY);
    }

    @Test
    @DisplayName("обновление пользователя по несуществующему id, негативный тест")
    void updateNegativeTest() {
        when(repository.findById(ENTITY.getId())).thenReturn(Optional.of(ENTITY));
        when(repository.save(ENTITY)).thenThrow(new RuntimeException("Ошибка сохранения данных"));

        assertThrows(RuntimeException.class, () ->
                accountDetailsIdService.update(ENTITY.getId(), DTO));
    }
}