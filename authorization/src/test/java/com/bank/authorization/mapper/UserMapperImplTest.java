package com.bank.authorization.mapper;

import com.bank.authorization.dto.UserDto;
import com.bank.authorization.entity.UserEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class UserMapperImplTest {

    UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    UserDto getUserDTO() {
        return new UserDto(10L, "User10", "123", 11111L);
    }

    UserEntity getUserEntity() {
        return new UserEntity(10L, "User10", 11111L, "123");
    }

    @Test
    @DisplayName("Маппинг в DTO")
    void toDTOTest() {
        UserEntity userEntity = getUserEntity();
        UserDto expectedDTO = getUserDTO();

        UserDto actualDto = userMapper.toDTO(userEntity);

        assertEquals(actualDto, expectedDTO);
    }

    @Test
    @DisplayName("Маппинг в DTO, на вход подается null")
    void toDtoNullTest() {
        UserDto actualDto = userMapper.toDTO(null);
        assertNull(actualDto);
    }

    @Test
    @DisplayName("Маппинг в entity")
    void toEntityTest() {
        UserEntity expectedEntity = getUserEntity();
        UserDto userDto = getUserDTO();

        UserEntity actualEntity = userMapper.toEntity(userDto);

        assertAll(() -> assertEquals(actualEntity.getProfileId(), expectedEntity.getProfileId()),
                () -> assertEquals(actualEntity.getRole(), expectedEntity.getRole()),
                () -> assertEquals(actualEntity.getPassword(), expectedEntity.getPassword())
                );
        }

    @Test
    @DisplayName("Маппинг в entity, на вход подается null")
    void toEntityNullTest() {
        UserEntity actualEntity = userMapper.toEntity(null);

        assertNull(actualEntity);
    }

    @Test
    @DisplayName("Маппинг списка в DTO")
    void toDtoListTest() {
        List<UserEntity> expectedEntityList = getUserEntityList();
        List<UserDto> expectedStoList = getUserDtoList();

        List<UserDto> actualDtoList = userMapper.toDtoList(expectedEntityList);

        assertEquals(actualDtoList, expectedStoList);
    }

    @Test
    @DisplayName("Маппинг списка в DTO, на вход подается null")
    void toDtoListNullTest() {
        List<UserDto> actualDtoEntityList = userMapper.toDtoList(null);

        assertNull(actualDtoEntityList);
    }

    @Test
    @DisplayName("Слияние в entity")
    void mergeToEntityTest() {
        UserEntity userEntity = getUserEntity();
        userEntity.setProfileId(111L);
        userEntity.setRole("Admin");
        userEntity.setPassword("123");
        UserDto actualDto = getUserDTO();
        UserEntity expectedEntity = getUserEntity();

        UserEntity actualEntity = userMapper.mergeToEntity(actualDto, userEntity);

        assertEquals(actualEntity, expectedEntity);
    }

    @Test
    @DisplayName("Слияние в entity, на вход подается null")
    void mergeToEntityNullTest() {
        UserEntity expectedEntity = getUserEntity();

        UserEntity actualEntity = userMapper.mergeToEntity(null, expectedEntity);
    }

    List<UserDto> getUserDtoList() {
        return new ArrayList<>(Arrays.asList(
                new UserDto(1L, "User1", "111", 1111L),
                new UserDto(2L, "User2", "111", 1111L),
                new UserDto(3L, "User3", "111", 1111L)
        ));
    }

    List<UserEntity> getUserEntityList() {
        return new ArrayList<>(Arrays.asList(
                new UserEntity(1L, "User1", 1111L, "111"),
                new UserEntity(2L, "User2", 1111L, "111"),
                new UserEntity(3L, "User3", 1111L, "111")
        ));
    }
}