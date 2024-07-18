package com.project.picpay.exceptions.transactionException.PayerDontHaveMoney;

public class PayerDontHaveMoneyException extends RuntimeException {
    public PayerDontHaveMoneyException(String message) {
        super(message);
    }
}
