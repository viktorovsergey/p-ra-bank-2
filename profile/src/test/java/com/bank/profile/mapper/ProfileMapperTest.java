package com.bank.profile.mapper;

import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.dto.PassportDto;
import com.bank.profile.dto.ProfileDto;
import com.bank.profile.entity.ActualRegistrationEntity;
import com.bank.profile.entity.PassportEntity;
import com.bank.profile.entity.ProfileEntity;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@FieldDefaults(level = AccessLevel.PRIVATE)
@ExtendWith(MockitoExtension.class)
@DisplayName("Тест класса ProfileMapper")
class ProfileMapperTest {
    final ProfileEntity ENTITY = new ProfileEntity(1L, 89008007766L, "test@mail.com", "Ivan Ivanov",
            123456789012L, 12312312432L, new PassportEntity(), new ActualRegistrationEntity());
    final ProfileDto DTO = new ProfileDto(1L, 89008007766L, "test@mail.com", "Ivan Ivanov",
            123456789012L, 12312312432L, new PassportDto(), new ActualRegistrationDto());
    final ProfileMapper MAPPER = Mappers.getMapper(ProfileMapper.class);

    @Test
    @DisplayName("маппинг в entity, на вход подан dto, позитивный тест")
    void toEntityPositiveTest() {
        ProfileEntity result = MAPPER.toEntity(DTO);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(DTO.getPhoneNumber(), result.getPhoneNumber()),
                () -> assertEquals(DTO.getEmail(), result.getEmail()),
                () -> assertEquals(DTO.getNameOnCard(), result.getNameOnCard()),
                () -> assertEquals(DTO.getInn(), result.getInn()),
                () -> assertEquals(DTO.getSnils(), result.getSnils())
        );
    }

    @Test
    @DisplayName("маппинг в entity, на вход подан null, негативный тест")
    void toEntityNegativeTest() {
        ProfileEntity result = MAPPER.toEntity(null);

        assertNull(result);
    }

    @Test
    @DisplayName("маппинг в List<dto>, на вход подан List<entity>, позитивный тест")
    void toDtoListPositiveTest() {
        List<ProfileEntity> entityList = new ArrayList<>();
        entityList.add(ENTITY);

        List<ProfileDto> result = MAPPER.toDtoList(entityList);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(ENTITY.getPhoneNumber(), result.get(0).getPhoneNumber()),
                () -> assertEquals(ENTITY.getEmail(), result.get(0).getEmail()),
                () -> assertEquals(ENTITY.getNameOnCard(), result.get(0).getNameOnCard()),
                () -> assertEquals(ENTITY.getInn(), result.get(0).getInn()),
                () -> assertEquals(ENTITY.getSnils(), result.get(0).getSnils())
        );
    }

    @Test
    @DisplayName("маппинг в List<dto>, на вход подан null, негативный тест")
    void toDtoListNegativeTest() {
        List<ProfileDto> result = MAPPER.toDtoList(null);

        assertNull(result);
    }

    @Test
    @DisplayName("слияние в entity, на вход подан dto и entity, позитивный тест")
    void mergeToEntityPositiveTest() {
        ProfileEntity result = MAPPER.mergeToEntity(DTO, ENTITY);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(ENTITY.getPhoneNumber(), result.getPhoneNumber()),
                () -> assertEquals(ENTITY.getEmail(), result.getEmail()),
                () -> assertEquals(ENTITY.getNameOnCard(), result.getNameOnCard()),
                () -> assertEquals(ENTITY.getInn(), result.getInn()),
                () -> assertEquals(ENTITY.getSnils(), result.getSnils())
        );
    }

    @Test
    @DisplayName("слияние в entity, на вход подан null, негативный тест")
    void mergeToEntityNegativeTest() {
        ProfileEntity result = MAPPER.mergeToEntity(null, null);

        assertNull(result);
    }
}