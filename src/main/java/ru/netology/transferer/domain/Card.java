package ru.netology.transferer.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Card {

    @NotBlank(message = "number must not be null")
    @Size(min = 16, max = 16)
    private String number;

    @NotBlank(message = "valid till must not be null")
    @Size(min = 5, max = 5)
    private String validTill;

    @NotBlank(message = "cvv must not be null")
    @Size(min = 3, max = 3)
    private String cvv;

    private Amount amount;
}