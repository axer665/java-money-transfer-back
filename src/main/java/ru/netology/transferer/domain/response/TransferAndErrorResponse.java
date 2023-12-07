package ru.netology.transferer.domain.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransferAndErrorResponse {
    private String message;
    private Integer id;
}