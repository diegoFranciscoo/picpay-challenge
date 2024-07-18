package com.project.picpay.handler;

import com.project.picpay.exceptions.transactionException.MerchantCannotSendMoney.MerchantCannotSendMoneyDetails;
import com.project.picpay.exceptions.transactionException.MerchantCannotSendMoney.MerchantCannotSendMoneyException;
import com.project.picpay.exceptions.transactionException.PayerDontHaveMoney.PayerDontHaveMoneyDetails;
import com.project.picpay.exceptions.transactionException.PayerDontHaveMoney.PayerDontHaveMoneyException;
import com.project.picpay.exceptions.transactionException.UnauthorizedTransaction.UnauthorizedTransactionDetails;
import com.project.picpay.exceptions.transactionException.UnauthorizedTransaction.UnauthorizedTransactionException;
import com.project.picpay.exceptions.userException.UserNotFound.UserNotFoundDetails;
import com.project.picpay.exceptions.userException.UserNotFound.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<UserNotFoundDetails> handlerUserNotFound(UserNotFoundException userNotFoundException){
        return new ResponseEntity<>(
                UserNotFoundDetails.builder()
                        .title("User not found.")
                        .details(userNotFoundException.getMessage())
                        .status(HttpStatus.NOT_FOUND.value())
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(MerchantCannotSendMoneyException.class)
    public ResponseEntity<MerchantCannotSendMoneyDetails> handlerMerchantCannotSendMoney(MerchantCannotSendMoneyException merchantCannotSendMoneyException) {
        return new ResponseEntity<>(
                MerchantCannotSendMoneyDetails.builder()
                        .title("Merchant cannot send money.")
                        .details(merchantCannotSendMoneyException.getMessage())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(PayerDontHaveMoneyException.class)
    public ResponseEntity<PayerDontHaveMoneyDetails> handlerPayerDontHaveMoney(PayerDontHaveMoneyException payerDontHaveMoneyException) {
        return new ResponseEntity<>(
                PayerDontHaveMoneyDetails.builder()
                        .title("Payer don't have money.")
                        .details(payerDontHaveMoneyException.getMessage())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(UnauthorizedTransactionException.class)
    public ResponseEntity<UnauthorizedTransactionDetails> handlerUnauthorizedTransaction(UnauthorizedTransactionException unauthorizedTransactionException) {
        return new ResponseEntity<>(
                UnauthorizedTransactionDetails.builder()
                        .title("Unauthorized Transaction.")
                        .details(unauthorizedTransactionException.getMessage())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .timestamp(LocalDateTime.now())
                        .build(), HttpStatus.BAD_REQUEST
        );
    }

}
