package ru.netology.transferer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.transferer.dto.request.TransferRequest;
import ru.netology.transferer.dto.request.ConfirmRequest;
import ru.netology.transferer.dto.response.TransferAndConfirmResponse;
import ru.netology.transferer.service.TransferMoneyService;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "${cross.origin.host.name}", maxAge = 3600)
public class TransferMoneyController {

    private final TransferMoneyService service;

    @PostMapping("/transfer")
    public TransferAndConfirmResponse postTransfer(@RequestBody TransferRequest transferRequest) {
        TransferAndConfirmResponse postTransfer = service.postTransfer(transferRequest);
        return new ResponseEntity<>(postTransfer, HttpStatus.OK).getBody();
    }

    @PostMapping("/confirmOperation")
    public TransferAndConfirmResponse postConfirmOperation(@RequestBody ConfirmRequest confirmRequest) {
        TransferAndConfirmResponse confirmResponse = service.postConfirmOperation(confirmRequest);
        return new ResponseEntity<>(confirmResponse, HttpStatus.OK).getBody();
    }
}