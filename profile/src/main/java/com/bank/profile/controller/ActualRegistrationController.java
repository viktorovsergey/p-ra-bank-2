package com.bank.profile.controller;

import com.bank.profile.dto.ActualRegistrationDto;
import com.bank.profile.entity.ActualRegistrationEntity;
import com.bank.profile.service.ActualRegistrationService;
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
 * Контроллер для {@link ActualRegistrationEntity}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/actual/registration")
@Tag(name = "Контроллер фактической регистрации",
        description = "Управляет операциями, свазанными с сущностью фактической регистрации")
public class ActualRegistrationController {

    private final ActualRegistrationService service;

    /**
     * @param id технический идентификатор {@link ActualRegistrationEntity}
     * @return {@link ResponseEntity<ActualRegistrationDto>}
     */
    @GetMapping("/read/{id}")
    @Operation(summary = "Получить информацию о фактической регистрации по id",
            description = "Возвращает объект ActualRegistrationDto по id")
    public ResponseEntity<ActualRegistrationDto> read(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    /**
     * @param actualRegistration {@link ActualRegistrationDto}
     * @return {@link ResponseEntity<ActualRegistrationDto>}
     */
    @PostMapping("/create")
    @Operation(summary = "Создать информацию о фактической регистрации",
            description = "Создает объект ActualRegistrationDto")
    public ResponseEntity<ActualRegistrationDto> create(@RequestBody ActualRegistrationDto actualRegistration) {
        return ResponseEntity.ok(service.save(actualRegistration));
    }

    /**
     * @param actualRegistration {@link ActualRegistrationDto}
     * @param id                 технический идентификатор {@link ActualRegistrationEntity}
     * @return {@link ResponseEntity<ActualRegistrationDto>}
     */
    @PutMapping("/update/{id}")
    @Operation(summary = "Обновить информацию о фактической регистрации по id",
            description = "Обновляет объект ActualRegistrationDto по id")
    public ResponseEntity<ActualRegistrationDto> update(@PathVariable Long id,
                                                        @RequestBody ActualRegistrationDto actualRegistration) {
        return ResponseEntity.ok(service.update(id, actualRegistration));
    }

    /**
     * @param ids лист технических идентификаторов {@link ActualRegistrationEntity}
     * @return {@link ResponseEntity} {@link List<ActualRegistrationDto>}
     */
    @GetMapping("read/all")
    @Operation(summary = "Получить информацию о фактической регистрации по списку id",
            description = "Получает объект ActualRegistrationDto по списку id")
    public ResponseEntity<List<ActualRegistrationDto>> readAllById(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(service.findAllById(ids));
    }
}
