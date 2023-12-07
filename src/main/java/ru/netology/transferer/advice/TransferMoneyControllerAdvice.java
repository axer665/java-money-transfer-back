package ru.netology.transferer.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.netology.transferer.exception.ConfirmException;
import ru.netology.transferer.exception.InputDataException;
import ru.netology.transferer.exception.TransferException;
import ru.netology.transferer.domain.response.TransferAndErrorResponse;

import java.util.concurrent.atomic.AtomicInteger;

@ControllerAdvice
public class TransferMoneyControllerAdvice {

    private final AtomicInteger id = new AtomicInteger();

    @ExceptionHandler(InputDataException.class)
    public ResponseEntity<TransferAndErrorResponse> handleInputData(InputDataException e) {
        return ResponseEntity.badRequest().body(new TransferAndErrorResponse(e.getMessage(), incrementAndGetId()));
    }

    @ExceptionHandler(TransferException.class)
    public ResponseEntity<TransferAndErrorResponse> handleTransfer(TransferException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new TransferAndErrorResponse(e.getMessage(), incrementAndGetId()));
    }

    @ExceptionHandler(ConfirmException.class)
    public ResponseEntity<TransferAndErrorResponse> handleConfirmation(ConfirmException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new TransferAndErrorResponse(e.getMessage(), incrementAndGetId()));
    }

    private int incrementAndGetId() {
        return id.incrementAndGet();
    }
}