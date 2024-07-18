package com.project.picpay.service;

import com.project.picpay.domain.user.User;
import com.project.picpay.domain.user.UserType;
import com.project.picpay.dto.TransactionDTO;
import com.project.picpay.exceptions.transactionException.MerchantCannotSendMoney.MerchantCannotSendMoneyException;
import com.project.picpay.exceptions.transactionException.PayerDontHaveMoney.PayerDontHaveMoneyException;
import com.project.picpay.exceptions.transactionException.UnauthorizedTransaction.UnauthorizedTransactionException;
import com.project.picpay.respository.TransactionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private UserService userService;
    @Mock
    private AuthorizationService authorizationService;


    @Test
    @DisplayName("Should create a new transaction when successfully")
    void makeTransaction_ReturnNewTransaction_WhenSuccessfully() {
        var payee = new User(1L, "testingPayer", new BigDecimal(10), "1234567", "testing@gmail.com", "1234", UserType.MERCHANT);
        var payer = new User(2L, "testingPayee", new BigDecimal(20), "12345678", "testing2@gmail.com", "12345", UserType.COMMON);

        when(userService.findById(1L)).thenReturn(payee);
        when(userService.findById(2L)).thenReturn(payer);

        when(authorizationService.authorizeTransaction()).thenReturn(true);

        TransactionDTO transaction = new TransactionDTO(new BigDecimal(10), 2L, 1L);
        transactionService.makeTransaction(transaction);

        payee.setBalance(new BigDecimal(20));
        payer.setBalance(new BigDecimal(10));

        verify(transactionRepository, times(1)).save(any());
        verify(userService, times(1)).saveUser(payee);
        verify(userService, times(1)).saveUser(payer);
    }

    @Test
    @DisplayName("Should throw UnauthorizedTransactionException when the transaction is not authorized")
    void IsAuthorize_ThrowUnauthorizedTransactionException_WhenTransactionIsNotAuthorized(){
        var payee = new User(1L, "testingPayer", new BigDecimal(10), "1234567", "testing@gmail.com", "1234", UserType.MERCHANT);
        var payer = new User(2L, "testingPayee", new BigDecimal(20), "12345678", "testing2@gmail.com", "12345", UserType.COMMON);

        when(userService.findById(1L)).thenReturn(payee);
        when(userService.findById(2L)).thenReturn(payer);
        when(authorizationService.authorizeTransaction()).thenReturn(false);

        var assertThrows = assertThrows(UnauthorizedTransactionException.class, () -> {
            TransactionDTO transaction = new TransactionDTO(new BigDecimal(10), 2L, 1L);
            transactionService.makeTransaction(transaction);
        });
        assertEquals("Unauthorized transaction.", assertThrows.getMessage());
    }
    @Test
    @DisplayName("Should throw MerchantCannotSendMoneyException when the merchant is the payer")
    void validatePayerTransaction_ThrowMerchantCannotSendMoneyException_WhenMerchantIsPayer(){
        var payer = new User(1L, "testingPayer", new BigDecimal(10), "1234567", "testing@gmail.com", "1234", UserType.MERCHANT);
        var payee = new User(2L, "testingPayee", new BigDecimal(20), "12345678", "testing2@gmail.com", "12345", UserType.COMMON);

        when(userService.findById(1L)).thenReturn(payer);
        when(userService.findById(2L)).thenReturn(payee);
        when(authorizationService.authorizeTransaction()).thenReturn(true);


        var assertThrows = assertThrows(MerchantCannotSendMoneyException.class, () -> {
            TransactionDTO transaction = new TransactionDTO(new BigDecimal(10), 1L, 2L);
            transactionService.makeTransaction(transaction);
        });

        assertEquals("Merchant cannot send money.", assertThrows.getMessage());
    }
    @Test
    @DisplayName("Should throw PayerDontHaveMoneyException when the payer doesn't have enough money")
    void validatePayerTransaction_ThrowPayerDontHaveMoneyException_WhenPayerDontHaveMoney(){
        var payee = new User(1L, "testingPayer", new BigDecimal(10), "1234567", "testing@gmail.com", "1234", UserType.MERCHANT);
        var payer = new User(2L, "testingPayee", new BigDecimal(20), "12345678", "testing2@gmail.com", "12345", UserType.COMMON);

        when(userService.findById(1L)).thenReturn(payee);
        when(userService.findById(2L)).thenReturn(payer);
        when(authorizationService.authorizeTransaction()).thenReturn(true);

        var assertThrows = assertThrows(PayerDontHaveMoneyException.class, () -> {
            TransactionDTO transaction = new TransactionDTO(new BigDecimal(100), 2L, 1L);
            transactionService.makeTransaction(transaction);
        });

        assertEquals("payer doesn't have enough money.", assertThrows.getMessage());
    }
}