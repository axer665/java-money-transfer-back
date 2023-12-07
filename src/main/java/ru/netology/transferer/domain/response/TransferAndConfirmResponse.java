package ru.netology.transferer.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferAndConfirmResponse {
    @NotBlank(message = "operation id must not be null")
    private String operationId;
}