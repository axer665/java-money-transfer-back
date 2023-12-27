package ru.netology.transferer.dto.request;

import ru.netology.transferer.domain.Amount;
import ru.netology.transferer.exception.InputDataException;

public record TransferRequest(String cardFromNumber, String cardFromValidTill, String cardFromCVV, String cardToNumber, Amount amount) {
    public TransferRequest{
        if (cardFromNumber.isBlank()){
            throw new InputDataException("Card from number must not be null");
        } else if (cardFromNumber.length() != 16) {
            throw new InputDataException("The card number mast consist of 16 digits");
        }

        if (cardFromValidTill.isBlank()) {
            throw new InputDataException("Card from valid till must not be null");
        } else if (cardFromValidTill.length() != 5) {
            throw new InputDataException("Card from valid till must contain 5 characters");
        }

        if (cardFromCVV.isBlank()) {
            throw new InputDataException("Card from cvv must not be null");
        } else if (cardFromCVV.length() != 3) {
            throw new InputDataException("Card from cvv must contain 3 characters");
        }

        if (cardToNumber.isBlank()) {
            throw new InputDataException("Card to number must not be null");
        } else if (cardToNumber.length() != 16) {
            throw new InputDataException("The card number mast consist of 16 digits");
        }
    }
}