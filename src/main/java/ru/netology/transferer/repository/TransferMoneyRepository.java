package ru.netology.transferer.repository;

import ru.netology.transferer.domain.Card;
import ru.netology.transferer.domain.request.TransferRequest;

public interface TransferMoneyRepository {

    Card getCard(String cardNumber);

    int incrementAndGetOperationId();

    void putTransfers(String id, TransferRequest transferRQ);

    void putCodes(String id, String code);

    TransferRequest removeTransfer(String id);

    String removeCode(String id);
}