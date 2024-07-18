package com.project.picpay.exceptions.transactionException.UnauthorizedTransaction;

import com.project.picpay.exceptions.ExceptionDetails;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class UnauthorizedTransactionDetails extends ExceptionDetails {
}
