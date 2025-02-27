package com.project.picpay.dto;

import java.math.BigDecimal;

public record TransactionDTO(

        BigDecimal value,
        Long payer,
        Long payee

) {
}
