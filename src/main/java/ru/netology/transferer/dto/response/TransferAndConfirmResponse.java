package ru.netology.transferer.dto.response;

import ru.netology.transferer.exception.InputDataException;

public record TransferAndConfirmResponse(String operationId) {
    public TransferAndConfirmResponse {
        if (operationId.isBlank()){
            throw new InputDataException("Operation id must not be null");
        }
    }
}