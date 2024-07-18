package com.project.picpay.exceptions.transactionException.MerchantCannotSendMoney;

public class MerchantCannotSendMoneyException extends RuntimeException {
    public MerchantCannotSendMoneyException(String message) {
        super(message);
    }
}
