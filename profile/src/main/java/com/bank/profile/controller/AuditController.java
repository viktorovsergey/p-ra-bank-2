package com.bank.profile.controller;

import com.bank.profile.dto.AuditDto;
import com.bank.profile.entity.AuditEntity;
import com.bank.profile.service.AuditService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для {@link AuditEntity}
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/audit")
@Tag(name = "Контроллер аудита",
        description = "Позволяет получить информацию о сущности Audit по id")
public class AuditController {

    private final AuditService service;

    /**
     * @param id технический идентификатор {@link AuditEntity}
     * @return {@link ResponseEntity<AuditDto>}
     */
    @GetMapping("/{id}")
    @Operation(summary = "Получить информацию об Audit entity по id",
            description = "Возвращает объект AuditDto по id")
    public AuditDto read(@PathVariable("id") Long id) {
        return service.findById(id);
    }
}
