package ru.netology.transferer.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.netology.transferer.domain.response.TransferAndConfirmResponse;
import ru.netology.transferer.repository.TransferMoneyRepositoryImplMock;

import static org.junit.jupiter.api.Assertions.*;
import static ru.netology.transferer.TestData.*;

@SpringBootTest
class TransferMoneyServiceTest {

    private final TransferMoneyService service = new TransferMoneyService(new TransferMoneyRepositoryImplMock());

    @Test
    void postTransfer() {
        TransferAndConfirmResponse expected = new TransferAndConfirmResponse(OPERATION_ID);
        TransferAndConfirmResponse actual = service.postTransfer(TRANSFER_RQ_1_2);
        assertEquals(expected, actual);
    }

    @Test
    void postConfirmOperation() {
        TransferAndConfirmResponse expected = new TransferAndConfirmResponse(OPERATION_ID);
        TransferAndConfirmResponse actual = service.postConfirmOperation(CONFIRM_RQ);
        assertEquals(expected, actual);
    }
}