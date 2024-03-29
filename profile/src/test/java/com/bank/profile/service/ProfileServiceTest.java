package com.bank.profile.service;

import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.dto.PassportDto;
import com.bank.profile.dto.ProfileDto;
import com.bank.profile.entity.ActualRegistrationEntity;
import com.bank.profile.entity.PassportEntity;
import com.bank.profile.entity.ProfileEntity;
import com.bank.profile.mapper.ProfileMapper;
import com.bank.profile.repository.ProfileRepository;
import com.bank.profile.service.impl.ProfileServiceImp;
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
@DisplayName("Тест класса ProfileService")
class ProfileServiceTest  {

    final ProfileEntity ENTITY = new ProfileEntity(1L, 89008007766L, "test@mail.com", "Ivan Ivanov",
            123456789012L, 12312312432L, new PassportEntity(), new ActualRegistrationEntity());
    final ProfileDto DTO = new ProfileDto(1L, 89008007766L, "test@mail.com", "Ivan Ivanov",
            123456789012L, 12312312432L, new PassportDto(), new ActualRegistrationDto());

    @Mock
    ProfileRepository repository;

    @Mock
    ProfileMapper mapper;

    @InjectMocks
    ProfileServiceImp profileService;

    @Test
    @DisplayName("поиск по существующему id, позитивный тест")
    void findByIdPositiveTest() {
        when(repository.findById(1L)).thenReturn(Optional.of(ENTITY));
        when(mapper.toDto(ENTITY)).thenReturn(DTO);

        ProfileDto result = profileService.findById(1L);

        assertNotNull(result);
        assertEquals(ENTITY.getId(), result.getId());
        verify(repository).findById(ENTITY.getId());
        verify(mapper).toDto(ENTITY);
    }

    @Test
    @DisplayName("поиск по несуществующему id, негативный тест")
    void findByIdNegativeTest() {
        when(repository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> profileService.findById(2L));
        verify(repository).findById(2L);
    }

    @Test
    @DisplayName("поиск List<dto> по существующему списку, позитивный тест")
    public void findAllByIdPositiveTest() {
        List<Long> ids = List.of(1L);
        List<ProfileEntity> entityList = List.of(ENTITY);
        List<ProfileDto> dtoList = List.of(DTO);

        when(repository.findAllById(ids)).thenReturn(entityList);
        when(mapper.toDtoList(entityList)).thenReturn(dtoList);

        List <ProfileDto> result = profileService.findAllById(ids);

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
                profileService.update(ENTITY.getId(), DTO));
    }

    @Test
    @DisplayName("сохранение корректных данных, позитивный тест")
    void savePositiveTest() {
        when(mapper.toEntity(DTO)).thenReturn(ENTITY);
        when(repository.save(ENTITY)).thenReturn(ENTITY);
        when(mapper.toDto(ENTITY)).thenReturn(DTO);

        ProfileDto result = profileService.save(DTO);

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

        RuntimeException exception = assertThrows(RuntimeException.class, () -> profileService.save(DTO));

        assertEquals("Ошибка сохранения данных", exception.getMessage());
    }

    @Test
    @DisplayName("обновление существующих данных, позитивный тест")
    public void updatePositiveTest() {

        when(repository.findById(ENTITY.getId())).thenReturn(Optional.of(ENTITY));
        when(mapper.mergeToEntity(DTO, ENTITY)).thenReturn(ENTITY);
        when(repository.save(ENTITY)).thenReturn(ENTITY);
        when(mapper.toDto(ENTITY)).thenReturn(DTO);

        ProfileDto result = profileService.update(ENTITY.getId(), DTO);

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
                profileService.update(ENTITY.getId(), DTO));
    }
}