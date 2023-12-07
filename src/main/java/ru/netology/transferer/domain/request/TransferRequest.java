package ru.netology.transferer.domain.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.netology.transferer.domain.Amount;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@AllArgsConstructor
public class TransferRequest {

    @NotBlank(message = "card from number must not be null")
    @Size(min = 16, max = 16)
    private String cardFromNumber;

    @NotBlank(message = "card from valid till must not be null")
    @Size(min = 5, max = 5)
    private String cardFromValidTill;

    @NotBlank(message = "card from cvv must not be null")
    @Size(min = 3, max = 3)
    private String cardFromCVV;

    @NotBlank(message = "card to number must not be null")
    @Size(min = 16, max = 16)
    private String cardToNumber;

    private Amount amount;
}