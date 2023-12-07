package ru.netology.transferer.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.netology.transferer.domain.log.Logger;
import ru.netology.transferer.enumerable.LogType;
import ru.netology.transferer.exception.InputDataException;
import ru.netology.transferer.domain.Card;
import ru.netology.transferer.domain.request.ConfirmRequest;
import ru.netology.transferer.domain.request.TransferRequest;
import ru.netology.transferer.domain.response.TransferAndConfirmResponse;
import ru.netology.transferer.repository.TransferMoneyRepository;

@Service
@AllArgsConstructor
public class TransferMoneyService {

    private final TransferMoneyRepository repository;
    private static final Logger LOGGER = Logger.getInstance();

    public TransferAndConfirmResponse postTransfer(TransferRequest transferRequest) {
        final Card cardFrom = repository.getCard(transferRequest.getCardFromNumber());
        final Card cardTo = repository.getCard(transferRequest.getCardToNumber());
        final int transferValue = transferRequest.getAmount().getValue();
        final int commission = (int) (transferValue * 0.01);

        final String transferId = Integer.toString(repository.incrementAndGetOperationId());

        if (cardFrom == null && cardTo != null) {
            LOGGER.log("Card from not found \n" +
                    String.format("Error transfer. Transfer id %s. Card from %s. Card to %s. Amount %d. Commission %d",
                            transferId, transferRequest.getCardFromNumber(), transferRequest.getCardToNumber(), transferValue, commission) + "\n", LogType.ERROR);
            throw new InputDataException("card from not found");
        } else if (cardFrom != null && cardTo == null) {
            LOGGER.log("Card to not found \n" +
                    String.format("Error transfer. Transfer operation id %s. Card from %s. Card to %s. Amount %d. Commission %d",
                            transferId, transferRequest.getCardFromNumber(), transferRequest.getCardToNumber(), transferValue, commission) + "\n", LogType.ERROR);
            throw new InputDataException("card to not found");
        } else if (cardFrom == null && cardTo == null) {
            LOGGER.log("Card from and card to not found \n" +
                    String.format("Error transfer. Transfer id %s. Card from %s. Card to %s. Amount %d. Commission %d",
                            transferId, transferRequest.getCardFromNumber(), transferRequest.getCardToNumber(), transferValue, commission) + "\n", LogType.ERROR);
            throw new InputDataException("card from and card to not found");
        }

        if (cardFrom == cardTo) {
            LOGGER.log("Two identical card numbers \n" +
                    String.format("Error transfer. Transfer id %s. Card from %s. Card to %s. Amount %d. Commission %d",
                            transferId, transferRequest.getCardFromNumber(), transferRequest.getCardToNumber(), transferValue, commission) + "\n", LogType.ERROR);

            throw new InputDataException("two identical card numbers");
        }

        final String cardFromValidTill = cardFrom.getValidTill();
        final String cardFromCVV = cardFrom.getCvv();
        final String transferRQCardFromValidTill = transferRequest.getCardFromValidTill();
        final String transferRQCardFromCVV = transferRequest.getCardFromCVV();

        if (!cardFromValidTill.equals(transferRQCardFromValidTill) && cardFromCVV.equals(transferRQCardFromCVV)) {
            LOGGER.log("Card from invalid data: valid till \n" +
                    String.format("Error transfer. Transfer id %s. Card from %s. Card to %s. Amount %d. Commission %d",
                            transferId, transferRequest.getCardFromNumber(), transferRequest.getCardToNumber(), transferValue, commission) + "\n", LogType.ERROR);
            throw new InputDataException("card from invalid data: valid till");
        } else if (cardFromValidTill.equals(transferRQCardFromValidTill) && !cardFromCVV.equals(transferRQCardFromCVV)) {
            LOGGER.log("Card from invalid data: cvv \n" +
                    String.format("Error transfer. Transfer id %s. Card from %s. Card to %s. Amount %d. Commission %d",
                            transferId, transferRequest.getCardFromNumber(), transferRequest.getCardToNumber(), transferValue, commission) + "\n", LogType.ERROR);
            throw new InputDataException("card from invalid data: cvv");
        } else if (!cardFromValidTill.equals(transferRQCardFromValidTill) && !cardFromCVV.equals(transferRQCardFromCVV)) {
            LOGGER.log("Card from invalid data: valid till and cvv \n" +
                    String.format("Error transfer. Transfer id %s. Card from %s. Card to %s. Amount %d. Commission %d",
                            transferId, transferRequest.getCardFromNumber(), transferRequest.getCardToNumber(), transferValue, commission) + "\n", LogType.ERROR);
            throw new InputDataException("card from invalid data: valid till and cvv");
        }

        if (cardFrom.getAmount().getValue() < transferRequest.getAmount().getValue()) {
            LOGGER.log("Card from invalid data: not enough money \n" +
                    String.format("Error transfer. Transfer id %s. Card from %s. Card to %s. Amount %d. Commission %d",
                            transferId, transferRequest.getCardFromNumber(), transferRequest.getCardToNumber(), transferValue, commission) + "\n", LogType.ERROR);
            throw new InputDataException("card from invalid data: not enough money");
        }

        repository.putTransfers(transferId, transferRequest);
        repository.putCodes(transferId, "0000");

        return new TransferAndConfirmResponse(transferId);
    }

    public TransferAndConfirmResponse postConfirmOperation(ConfirmRequest confirmRQ) {
        final String operationId = confirmRQ.getOperationId();
        final TransferRequest transferRequest = repository.removeTransfer(operationId);

        final Card cardFrom = repository.getCard(transferRequest.getCardFromNumber());
        final Card cardTo = repository.getCard(transferRequest.getCardToNumber());

        final int cardFromValue = cardFrom.getAmount().getValue();
        final int cardToValue = cardTo.getAmount().getValue();
        final int transferValue = transferRequest.getAmount().getValue();
        final int commission = (int) (transferValue * 0.01);

        if (transferRequest == null) {
            LOGGER.log("Confirm operation invalid data: operation not found \n" +
                    String.format("Error transfer. Transfer operation id %s. Card from %s. Card to %s. Amount %d. Commission %d",
                            operationId, transferRequest.getCardFromNumber(), transferRequest.getCardToNumber(), transferValue, commission) + "\n", LogType.ERROR);
            throw new InputDataException("Confirm operation invalid data: operation not found");
        }

        final String code = repository.removeCode(operationId);
        if (!confirmRQ.getCode().equals(code) || code.isEmpty()) {
            LOGGER.log("Confirm operation invalid data: code \n" +
                    String.format("Error transfer. Transfer operation id %s. Card from %s. Card to %s. Amount %d. Commission %d",
                            operationId, transferRequest.getCardFromNumber(), transferRequest.getCardToNumber(), transferValue, commission) + "\n", LogType.ERROR);
            throw new InputDataException("Confirm operation invalid data: code");
        }

        cardFrom.getAmount().setValue(cardFromValue - transferValue);
        cardTo.getAmount().setValue(cardToValue + transferValue - commission);

        LOGGER.log(String.format("Success transfer. \n " +
                        "Transfer operation id %s. Card from %s. Card to %s. Amount %d. Commission %d",
                operationId, transferRequest.getCardFromNumber(), transferRequest.getCardToNumber(), transferValue, commission) + "\n", LogType.INFO);

        return new TransferAndConfirmResponse(operationId);
    }
}