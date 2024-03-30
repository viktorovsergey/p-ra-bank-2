package com.bank.authorization.service;

import com.bank.authorization.dto.UserDto;
import com.bank.authorization.entity.UserEntity;
import com.bank.authorization.mapper.UserMapper;
import com.bank.authorization.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserMapper userMapper;

    @Test
    @DisplayName("Поиск по id, позитивный сценарий")
    void findByIdPositiveTest() {
        long id = 1L;
        UserEntity userEntity = new UserEntity();
        UserDto userDto = new UserDto();
        when(userRepository.findById(id)).thenReturn(Optional.of(userEntity));
        when(userMapper.toDTO(userEntity)).thenReturn(userDto);

        UserDto resultDto = userService.findById(1L);
        assertEquals(userDto, resultDto);
    }

    @Test
    @DisplayName("Поиск по id null, негативный сценарий")
    void findByIdNegativeTest() {
        long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.findById(id));
    }

    @Test
    @DisplayName("Создание User, позитивный сценарий")
    void saveUserPositiveTest() {
        UserDto userDto = new UserDto();
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword("123");
        when(userMapper.toEntity(userDto)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userMapper.toDTO(userEntity)).thenReturn(userDto);

        UserDto resultDto = userService.save(userDto);

        assertEquals(resultDto, userDto);
    }

    @Test
    @DisplayName("Создание User null, негативный сценарий")
    void saveNullUserNegativeTest() {
        UserDto userDto = null;

        assertThrows(NullPointerException.class, () -> userService.save(userDto));
    }

    @Test
    @DisplayName("Изменение User по id, позитивный сценарий")
    void updateByIdPositiveTest() {
        long id = 1L;
        UserDto userDto = new UserDto();
        UserEntity userEntity = new UserEntity();
        UserEntity updatedEntity = new UserEntity();
        when(userRepository.findById(id)).thenReturn(Optional.of(userEntity));
        when(userMapper.mergeToEntity(userDto, userEntity)).thenReturn(updatedEntity);
        when(userRepository.save(updatedEntity)).thenReturn(updatedEntity);
        when(userMapper.toDTO(updatedEntity)).thenReturn(userDto);

        UserDto resultDto = userService.update(id, userDto);

        assertEquals(resultDto, userDto);
    }

    @Test
    @DisplayName("Изменение User, негативный сценарий")
    void updateByIdNegativeTest() {
        long id = 1L;
        UserDto userDto = new UserDto();

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.update(id, userDto));
    }

    @Test
    @DisplayName("Получение списка User по списку id, позитивный сценарий")
    void findAllByIdsPositiveTest() {
        List<Long> idList = Arrays.asList(1L, 2L);
        List<UserEntity> userEntityList = Arrays.asList(new UserEntity(), new UserEntity());
        List<UserDto> expectedUserDtoList = Arrays.asList(new UserDto(), new UserDto());

        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntityList.get(0)));
        when(userRepository.findById(2L)).thenReturn(Optional.of(userEntityList.get(1)));
        when(userMapper.toDtoList(userEntityList)).thenReturn(expectedUserDtoList);

        List<UserDto> resultDtoList = userService.findAllByIds(idList);

        assertEquals(expectedUserDtoList, resultDtoList);
    }

    @Test
    @DisplayName("Получение списка User по списку id с несуществующим id, негативный сценарий")
    void findAllByIdsNegativeTest() {
        List<Long> idList = Arrays.asList(1L, 2L, 3L);
        List<UserEntity> userEntityList = Arrays.asList(new UserEntity(), new UserEntity());

        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntityList.get(0)));
        when(userRepository.findById(2L)).thenReturn(Optional.of(userEntityList.get(1)));
        when(userRepository.findById(3L)).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(
                EntityNotFoundException.class,
                () -> userService.findAllByIds(idList));

        assertThat("Не был найден пользователь с ID " + 3L).isEqualTo(exception.getMessage());
    }

}