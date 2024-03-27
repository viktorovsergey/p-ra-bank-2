package com.bank.account.controller;

import com.bank.account.dto.AccountDetailsDto;
import com.bank.account.entity.AccountDetailsEntity;
import com.bank.account.service.AccountDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Контроллер для {@link AccountDetailsEntity}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/details")
@Tag(name = "Контроллер банковских счетов",
        description = "Управляет операциями по банковским счетам")
public class AccountDetailsController {

    private final AccountDetailsService service;

    /**
     * @param id технический идентификатор {@link AccountDetailsEntity}
     * @return {@link ResponseEntity<AccountDetailsDto>}
     */
    @GetMapping("/{id}")
    @Operation(summary = "Получение информации о банковском счете по id",
            description = "Возвращает объект AccountDetailsDto по id")
    public AccountDetailsDto read(@PathVariable("id") Long id) {
        return service.findById(id);
    }

    /**
     * @param accountDetails - сущность для создания в виде {@link AccountDetailsDto}
     * @return {@link ResponseEntity<AccountDetailsDto>}
     */
    @PostMapping("/create")
    @Operation(summary = "Создание банковского счета",
            description = "Создает банковский счет на основе переданных данных")
    public ResponseEntity<AccountDetailsDto> create(@RequestBody AccountDetailsDto accountDetails) {
        return ResponseEntity.ok(service.save(accountDetails));
    }

    /**
     * @param accountDetails {@link AccountDetailsDto}
     * @param id             технический идентификатор {@link AccountDetailsEntity}
     * @return {@link ResponseEntity<AccountDetailsDto>}
     */
    @PutMapping("/update/{id}")
    @Operation(summary = "Обновление банковского счета",
            description = "Обновляет информацию о банковском счете по id на основе переданных данных")
    public ResponseEntity<AccountDetailsDto> update(@PathVariable Long id,
                                                    @RequestBody AccountDetailsDto accountDetails) {
        return ResponseEntity.ok(service.update(id, accountDetails));
    }

    /**
     * @param ids лист технических идентификаторов {@link AccountDetailsEntity}
     * @return {@link ResponseEntity} c {@link List<AccountDetailsDto>}.
     */
    @GetMapping("read/all")
    @Operation(summary = "Получение всех банковских счетов по списку id",
            description = "Возвращает список банковских счетов на основе переданных id")
    public ResponseEntity<List<AccountDetailsDto>> readAll(@RequestParam List<Long> ids) {
        return ResponseEntity.ok(service.findAllById(ids));
    }
}
