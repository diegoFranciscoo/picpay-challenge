package com.project.picpay.controller;

import com.project.picpay.domain.transaction.Transaction;
import com.project.picpay.dto.TransactionDTO;
import com.project.picpay.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transaction> makeTransaction(@RequestBody TransactionDTO transactionDTO) {
        return new ResponseEntity<>(transactionService.makeTransaction(transactionDTO), HttpStatus.CREATED);
    }
}
