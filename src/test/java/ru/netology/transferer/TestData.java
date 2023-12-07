package ru.netology.transferer;

import ru.netology.transferer.domain.Amount;
import ru.netology.transferer.domain.Card;
import ru.netology.transferer.domain.request.ConfirmRequest;
import ru.netology.transferer.domain.request.TransferRequest;

public class TestData {

    public static final String CARD_NUMBER_1 = "1111111111111111";
    public static final String CARD_VALID_TILL_1 = "12/25";
    public static final String CARD_CVV_1 = "123";
    public static final Card CARD_1 = new Card(CARD_NUMBER_1, CARD_VALID_TILL_1, CARD_CVV_1, new Amount(2500_00, "RUR"));
    public static final Card CARD_1_1 = new Card(CARD_NUMBER_1, CARD_VALID_TILL_1, CARD_CVV_1, new Amount(2500_00, "RUR"));


    public static final String CARD_NUMBER_2 = "2222222222222222";
    public static final String CARD_VALID_TILL_2 = "12/25";
    public static final String CARD_CVV_2 = "123";
    public static final Card CARD_2 = new Card(CARD_NUMBER_2, CARD_VALID_TILL_2, CARD_CVV_2, new Amount(2000_00, "RUR"));
    public static final Card CARD_2_2 = new Card(CARD_NUMBER_2, CARD_VALID_TILL_2, CARD_CVV_2, new Amount(2000_00, "RUR"));

    public static final String CARD_NUMBER_3 = "3333333333333333";
    public static final String CARD_VALID_TILL_3 = "12/25";
    public static final String CARD_CVV_3 = "123";
    public static final Card CARD_3 = new Card(CARD_NUMBER_3, CARD_VALID_TILL_3, CARD_CVV_3, new Amount(1500_00, "RUR"));

    public static final String CARD_NUMBER_4 = "4444444444444444";
    public static final String CARD_VALID_TILL_4 = "12/25";
    public static final String CARD_CVV_4 = "123";

    public static final String CARD_NUMBER_5 = "5555555555555555";

    public static final String OPERATION_ID = "1";
    public static final String CODE = "0000";

    public static final TransferRequest TRANSFER_RQ_1_2 = new TransferRequest(CARD_NUMBER_1, CARD_VALID_TILL_1, CARD_CVV_1, CARD_NUMBER_2, new Amount(100_00, "RUR"));
    public static final TransferRequest TRANSFER_RQ_4_5 = new TransferRequest(CARD_NUMBER_4, CARD_VALID_TILL_4, CARD_CVV_4, CARD_NUMBER_5, new Amount(100_00, "RUR"));
    public static final ConfirmRequest CONFIRM_RQ = new ConfirmRequest(OPERATION_ID, CODE);
}