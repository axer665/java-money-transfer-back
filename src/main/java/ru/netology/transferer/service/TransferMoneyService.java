package ru.netology.transferer.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.netology.transferer.log.Logger;
import ru.netology.transferer.enumerable.LogType;
import ru.netology.transferer.exception.InputDataException;
import ru.netology.transferer.domain.Card;
import ru.netology.transferer.dto.request.ConfirmRequest;
import ru.netology.transferer.dto.request.TransferRequest;
import ru.netology.transferer.dto.response.TransferAndConfirmResponse;
import ru.netology.transferer.repository.TransferMoneyRepository;

@Service
@AllArgsConstructor
public class TransferMoneyService {
    private final double COMISSION = 0.01;
    private final TransferMoneyRepository repository;
    private static final Logger LOGGER = Logger.getInstance();

    public TransferAndConfirmResponse postTransfer(TransferRequest transferRequest) {
        final String transferId = Integer.toString(repository.incrementAndGetOperationId());

        loggingTransferResult("Transfer start: ", transferId, transferRequest.cardFromNumber(), transferRequest.cardToNumber(), transferRequest.amount().getValue());

        final Card cardFrom = repository.getCard(transferRequest.cardFromNumber());
        final Card cardTo = repository.getCard(transferRequest.cardToNumber());

        checkCard(cardFrom, cardTo, transferId);
        checkCardFromData(cardFrom, transferRequest, transferId);

        repository.putTransfers(transferId, transferRequest);
        repository.putCodes(transferId, "0000");

        return new TransferAndConfirmResponse(transferId);
    }

    public TransferAndConfirmResponse postConfirmOperation(ConfirmRequest confirmRequest) {
        final String operationId = confirmRequest.operationId();

        final TransferRequest transferRequest = repository.removeTransfer(operationId);
        final String code = repository.removeCode(operationId);

        final Card cardFrom = repository.getCard(transferRequest.cardFromNumber());
        final Card cardTo = repository.getCard(transferRequest.cardToNumber());

        final int cardFromValue = cardFrom.getAmount().getValue();
        final int cardToValue = cardTo.getAmount().getValue();
        final int transferValue = transferRequest.amount().getValue();
        final int commission = (int) (transferValue * COMISSION);

        checkTransfer(transferRequest);
        checkCode(code, confirmRequest);

        cardFrom.getAmount().setValue(cardFromValue - transferValue);
        cardTo.getAmount().setValue(cardToValue + transferValue - commission);

        LOGGER.log("Transfer#"+operationId+": Success transfer \n", LogType.INFO);

        return new TransferAndConfirmResponse(operationId);
    }

    private boolean checkCard(Card cardFrom, Card cardTo, String transferId) {
        if (cardFrom == null) {
            LOGGER.log("Transfer#"+transferId+": Card from not found \n", LogType.ERROR);
            throw new InputDataException("card from not found");
        } else if (cardFrom != null && cardTo == null) {
            LOGGER.log("Transfer#"+transferId+": Card to not found \n", LogType.ERROR);
            throw new InputDataException("card to not found");
        } else if (cardFrom == cardTo) {
            LOGGER.log("Transfer#"+transferId+": Two identical card numbers \n", LogType.ERROR);
            throw new InputDataException("two identical card numbers");
        }
        return true;
    }

    private boolean checkCardFromData(Card cardFrom, TransferRequest transferRequest, String transferId) {
        final String cardFromValidTill = cardFrom.getValidTill();
        final String cardFromCVV = cardFrom.getCvv();
        final String transferRQCardFromValidTill = transferRequest.cardFromValidTill();
        final String transferRQCardFromCVV = transferRequest.cardFromCVV();


        if (!cardFromValidTill.equals(transferRQCardFromValidTill)) {
            LOGGER.log("Transfer#"+transferId+": Card from invalid data: valid till \n", LogType.ERROR);
            throw new InputDataException("Card from invalid data: valid till");
        } else if (!cardFromCVV.equals(transferRQCardFromCVV)) {
            LOGGER.log("Transfer#"+transferId+": Card from invalid data: cvv \n", LogType.ERROR);
            throw new InputDataException("Card from invalid data: cvv");
        } else if (cardFrom.getAmount().getValue() < transferRequest.amount().getValue()){
            LOGGER.log("Transfer#"+transferId+": Card from invalid data: not enough money \n", LogType.ERROR);
            throw new InputDataException("Card from invalid data: not enough money");
        }

        return true;
    }

    private boolean checkTransfer(TransferRequest transferRequest) {
        if (transferRequest == null) {
            LOGGER.log("Confirm operation invalid data: operation not found \n", LogType.ERROR);
            throw new InputDataException("Confirm operation invalid data: operation not found");
        }
        return true;
    }

    private boolean checkCode(String code, ConfirmRequest confirmRequest) {
        if (!confirmRequest.code().equals(code) || code.isEmpty()) {
            LOGGER.log("Transfer#"+confirmRequest.operationId()+": Confirm operation invalid data: code \n", LogType.ERROR);
            throw new InputDataException("Confirm operation invalid data: code");
        }
        return true;
    }

    private void loggingTransferResult(String message, String transactionId, String cardFrom, String cardTo, int transferValue) {
        final int commission = (int) (transferValue * COMISSION);
        LOGGER.log(String.format(message + "\n" +
                        "Transfer id %s. Card from %s. Card to %s. Amount %d. Commission %d",
                transactionId, cardFrom, cardTo, transferValue, commission) + "\n");

    }
}