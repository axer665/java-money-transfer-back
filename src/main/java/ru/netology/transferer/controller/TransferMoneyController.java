package ru.netology.transferer.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.transferer.domain.request.TransferRequest;
import ru.netology.transferer.domain.request.ConfirmRequest;
import ru.netology.transferer.domain.response.TransferAndConfirmResponse;
import ru.netology.transferer.service.TransferMoneyService;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "${cross.origin.host.name}", maxAge = 3600)
public class TransferMoneyController {

    private TransferMoneyService service;

    @PostMapping("/transfer")
    public TransferAndConfirmResponse postTransfer(@RequestBody TransferRequest transferRequest) {
        return service.postTransfer(transferRequest);
    }

    @PostMapping("/confirmOperation")
    public TransferAndConfirmResponse postConfirmOperation(@RequestBody ConfirmRequest confirmRequest) {
        return service.postConfirmOperation(confirmRequest);
    }
}