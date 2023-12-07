package ru.netology.transferer.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class ConfirmRequest {
    @NotBlank(message = "operation id must not be null")
    private String operationId;

    @NotBlank(message = "code must not be null")
    private String code;
}