package ru.netology.transferer.repository;

import ru.netology.transferer.domain.Card;
import ru.netology.transferer.domain.request.TransferRequest;

import static ru.netology.transferer.TestData.*;

public class TransferMoneyRepositoryImplMock implements TransferMoneyRepository {

    @Override
    public Card getCard(String cardNumber) {
        if (cardNumber.equals(CARD_NUMBER_1)) {
            return CARD_1;
        }
        if (cardNumber.equals(CARD_NUMBER_2)) {
            return CARD_2;
        }
        return null;
    }

    @Override
    public int incrementAndGetOperationId() {
        return Integer.parseInt(OPERATION_ID);
    }

    @Override
    public void putTransfers(String id, TransferRequest transferRQ) {
    }

    @Override
    public void putCodes(String id, String code) {
    }

    @Override
    public TransferRequest removeTransfer(String id) {
        if (id.equals(OPERATION_ID)) {
            return TRANSFER_RQ_1_2;
        }
        return null;
    }

    @Override
    public String removeCode(String id) {
        if (id.equals(OPERATION_ID)) {
            return CODE;
        }
        return null;
    }
}