package com.bank.profile.controller;

import com.bank.profile.dto.RegistrationDto;
import com.bank.profile.entity.RegistrationEntity;
import com.bank.profile.service.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Контроллер для {@link RegistrationEntity}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/registration")
@Tag(name = "Контроллер регистрации",
        description = "Управляет операциями, свазанными с сущностью Registration")
public class RegistrationController {

    private final RegistrationService service;

    /**
     * @param id технический идентификатор {@link RegistrationEntity}
     * @return {@link ResponseEntity<RegistrationDto>}
     */
    @GetMapping("/read/{id}")
    @Operation(summary = "Получить информацию о регистрации по id",
            description = "Возвращает объект RegistrationDto по id")
    public ResponseEntity<RegistrationDto> read(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /**
     * @param registration {@link RegistrationDto}
     * @return {@link ResponseEntity<RegistrationDto>}
     */
    @PostMapping("/create")
    @Operation(summary = "Создать информацию о фактической регистрации",
            description = "Создает объект RegistrationDto")
    public ResponseEntity<RegistrationDto> create(@RequestBody RegistrationDto registration) {
        return ResponseEntity.ok(service.save(registration));
    }

    /**
     * @param registration {@link RegistrationDto}
     * @param id           технический идентификатор {@link RegistrationEntity}
     * @return {@link ResponseEntity<RegistrationDto>}
     */
    @PutMapping("/update/{id}")
    @Operation(summary = "Обновить информацию о регистрации по id",
            description = "Обновляет объект RegistrationDto по id")
    public ResponseEntity<RegistrationDto> update(@PathVariable Long id, @RequestBody RegistrationDto registration) {
        return ResponseEntity.ok(service.update(id, registration));
    }

    /**
     * @param ids лист технических идентификаторов {@link RegistrationEntity}
     * @return {@link ResponseEntity} {@link List<RegistrationDto>}
     */
    @GetMapping("read/all")
    @Operation(summary = "Получить информацию о регистрации по списку id",
            description = "Получает объект RegistrationDto по списку id")
    public ResponseEntity<List<RegistrationDto>> readAllById(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(service.findAllById(ids));
    }
}
