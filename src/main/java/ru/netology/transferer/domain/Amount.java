package ru.netology.transferer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class Amount {

    @Min(0)
    private Integer value;

    @NotBlank(message = "currency must not be null")
    private String currency;
}