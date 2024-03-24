package com.bank.account.service;

import com.bank.account.dto.AccountDetailsDto;
import com.bank.account.entity.AccountDetailsEntity;
import com.bank.account.mapper.AccountDetailsMapper;
import com.bank.account.repository.AccountDetailsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@DisplayName("Тест класса AccountDetailsService")
@ExtendWith(MockitoExtension.class)
public class AccountDetailsServiceTest {

    final AccountDetailsDto ACCOUNT_DETAILS_DTO = new AccountDetailsDto(1L, 2L, 3L,
            4L, new BigDecimal(4), false, 7L);
    final AccountDetailsEntity ACCOUNT_DETAILS_ENTITY = new AccountDetailsEntity(1L, 2L, 3L,
            4L, new BigDecimal(4), false, 7L);
    private final List<AccountDetailsEntity> LIST_ENTITY = List.of(ACCOUNT_DETAILS_ENTITY);
    private final List<AccountDetailsDto> LIST_DTO = List.of(ACCOUNT_DETAILS_DTO);

    @Mock
    AccountDetailsRepository repository;
    @Mock
    AccountDetailsMapper mapper;
    @InjectMocks
    AccountDetailsServiceImpl accountDetailsService;

    @Test
    @DisplayName("поиск по id, позитивный тест")
    public void findByIdPositiveTest() {
        doReturn(Optional.of(ACCOUNT_DETAILS_ENTITY)).when(this.repository).findById(ACCOUNT_DETAILS_ENTITY.getId());
        doReturn(ACCOUNT_DETAILS_DTO).when(this.mapper).toDto(ACCOUNT_DETAILS_ENTITY);

        AccountDetailsDto result = accountDetailsService.findById(ACCOUNT_DETAILS_ENTITY.getId());
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(ACCOUNT_DETAILS_DTO, result),
                () -> verify(mapper).toDto(any()),
                () -> verify(repository).findById(any())
        );
    }

    @Test
    @DisplayName("поиск по id, негативный тест")
    public void findByIdNegativeTest() {
        when(repository.findById(any())).thenReturn(Optional.empty());
        NullPointerException exception = assertThrows(NullPointerException.class, () -> accountDetailsService.findById(1L));
        verify(repository).findById(any());
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("поиск list_dto по существующему списку id, позитивный тест")
    public void findAllByIdTest() {
        doReturn(Optional.of(ACCOUNT_DETAILS_ENTITY)).when(this.repository).findById(ACCOUNT_DETAILS_ENTITY.getId());
        doReturn(LIST_DTO).when(this.mapper).toDtoList(LIST_ENTITY);

        List <AccountDetailsDto> result = accountDetailsService.findAllById(List.of(ACCOUNT_DETAILS_ENTITY.getId()));
        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(LIST_DTO.size(), result.size()),
                () -> verify(mapper).toDtoList(any()),
                () -> verify(repository).findById(any())
        );
    }

    @Test
    @DisplayName("поиск list_dto по несуществующему списку id, негативный тест")
    public void findAllByIdNegativeTest() {
        when(repository.findById(any())).thenReturn(Optional.empty());
        NullPointerException exception = assertThrows(NullPointerException.class, () -> accountDetailsService.findAllById(Arrays.asList(1L)));
        verify(repository).findById(any());
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("сохранение корректных данных, позитивный тест")
    public void savePositiveTest() {
        when(mapper.toEntity(ACCOUNT_DETAILS_DTO)).thenReturn(ACCOUNT_DETAILS_ENTITY);
        when(repository.save(ACCOUNT_DETAILS_ENTITY)).thenReturn(ACCOUNT_DETAILS_ENTITY);
        when(mapper.toDto(ACCOUNT_DETAILS_ENTITY)).thenReturn(ACCOUNT_DETAILS_DTO);

        AccountDetailsDto result = accountDetailsService.save(ACCOUNT_DETAILS_DTO);

        verify(mapper).toEntity(ACCOUNT_DETAILS_DTO);
        verify(repository).save(ACCOUNT_DETAILS_ENTITY);
        verify(mapper).toDto(ACCOUNT_DETAILS_ENTITY);
        assertEquals(ACCOUNT_DETAILS_DTO, result);
    }

    @Test
    @DisplayName("сохранение некорректных данных, негативный тест")
    public void saveNegativeTest() {
        when(mapper.toEntity(ACCOUNT_DETAILS_DTO)).thenReturn(ACCOUNT_DETAILS_ENTITY);
        when(repository.save(ACCOUNT_DETAILS_ENTITY)).thenThrow(new RuntimeException("Failed to save entity"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> accountDetailsService.save(ACCOUNT_DETAILS_DTO));

        assertEquals("Failed to save entity", exception.getMessage());
    }

    @Test
    @DisplayName("обновление данных, позитивный тест")
    public void updatePositiveTest() {

        when(repository.findById(ACCOUNT_DETAILS_ENTITY.getId())).thenReturn(Optional.of(ACCOUNT_DETAILS_ENTITY));
        when(mapper.mergeToEntity(ACCOUNT_DETAILS_ENTITY, ACCOUNT_DETAILS_DTO)).thenReturn(ACCOUNT_DETAILS_ENTITY);
        when(repository.save(ACCOUNT_DETAILS_ENTITY)).thenReturn(ACCOUNT_DETAILS_ENTITY);
        when(mapper.toDto(ACCOUNT_DETAILS_ENTITY)).thenReturn(ACCOUNT_DETAILS_DTO);

        AccountDetailsDto result = accountDetailsService.update(ACCOUNT_DETAILS_ENTITY.getId(), ACCOUNT_DETAILS_DTO);

        verify(repository).findById(ACCOUNT_DETAILS_ENTITY.getId());
        verify(mapper).mergeToEntity(ACCOUNT_DETAILS_ENTITY, ACCOUNT_DETAILS_DTO);
        verify(repository).save(ACCOUNT_DETAILS_ENTITY);
        verify(mapper).toDto(ACCOUNT_DETAILS_ENTITY);
    }

    @Test
    @DisplayName("обновление данных, негативный тест")
    public void updateNegativeTest() {

        when(repository.findById(ACCOUNT_DETAILS_ENTITY.getId())).thenReturn(Optional.of(ACCOUNT_DETAILS_ENTITY));
        when(repository.save(ACCOUNT_DETAILS_ENTITY)).thenThrow(new RuntimeException("Failed to save entity"));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                accountDetailsService.update(ACCOUNT_DETAILS_ENTITY.getId(), ACCOUNT_DETAILS_DTO));
    }

}