package com.project.picpay.service;

import com.project.picpay.domain.transaction.Transaction;
import com.project.picpay.domain.user.User;
import com.project.picpay.domain.user.UserType;
import com.project.picpay.dto.TransactionDTO;
import com.project.picpay.exceptions.transactionException.MerchantCannotSendMoney.MerchantCannotSendMoneyException;
import com.project.picpay.exceptions.transactionException.PayerDontHaveMoney.PayerDontHaveMoneyException;
import com.project.picpay.exceptions.transactionException.UnauthorizedTransaction.UnauthorizedTransactionException;
import com.project.picpay.respository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserService userService;
    private final AuthorizationService authorizationService;

    public Transaction makeTransaction(TransactionDTO transactionDTO) {
        User payer = userService.findById(transactionDTO.payer());
        User payee = userService.findById(transactionDTO.payee());
        validatePayerTransaction(payer, transactionDTO.value());

        boolean isAuthorize = authorizationService.authorizeTransaction();
        isAuthorize(isAuthorize);

        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transactionDTO.value());
        newTransaction.setPayee(payee);
        newTransaction.setPayer(payer);
        newTransaction.setTimestamp(LocalDateTime.now());

        BigDecimal newBalancePayer = payer.getBalance().subtract(transactionDTO.value());
        BigDecimal newBalancePayee = payee.getBalance().add(transactionDTO.value());

        payee.setBalance(newBalancePayee);
        payer.setBalance(newBalancePayer);

        transactionRepository.save(newTransaction);
        userService.saveUser(payee);
        userService.saveUser(payer);

        return newTransaction;
    }

    private void validatePayerTransaction(User payer, BigDecimal amount) {
        if (payer.getUserType() == UserType.MERCHANT) throw new MerchantCannotSendMoneyException("Merchant cannot send money.");
        if (payer.getBalance().compareTo(amount) < 0) throw new PayerDontHaveMoneyException("payer doesn't have enough money.");
    }

    private void isAuthorize(boolean authorize) {
        if (!authorize) {
            throw new UnauthorizedTransactionException("Unauthorized transaction.");
        }
    }
}
