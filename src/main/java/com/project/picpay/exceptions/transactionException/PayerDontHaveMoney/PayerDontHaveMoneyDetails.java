package com.project.picpay.exceptions.transactionException.PayerDontHaveMoney;

import com.project.picpay.exceptions.ExceptionDetails;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class PayerDontHaveMoneyDetails extends ExceptionDetails {
}
