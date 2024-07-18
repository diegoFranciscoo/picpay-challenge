package com.project.picpay.exceptions.transactionException.UnauthorizedTransaction;

public class UnauthorizedTransactionException extends RuntimeException{
    public UnauthorizedTransactionException(String message) {
        super(message);
    }
}
