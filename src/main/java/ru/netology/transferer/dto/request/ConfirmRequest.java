package ru.netology.transferer.dto.request;

import ru.netology.transferer.exception.InputDataException;

public record ConfirmRequest(String operationId, String code){
    public ConfirmRequest{
        if (operationId.isBlank()){
            throw new InputDataException("operation id must not be null");
        } else if (code.isBlank()) {
            throw new InputDataException("code must not be null");
        }
    }
};