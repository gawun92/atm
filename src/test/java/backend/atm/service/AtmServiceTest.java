package backend.atm.service;

import backend.atm.db.entity.Account;
import backend.atm.db.entity.Card;
import backend.atm.db.repo.AccountRepo;
import backend.atm.db.repo.CardRepo;
import backend.atm.enumerate.AccountStatus;
import backend.atm.enumerate.AuthCardStatus;
import backend.atm.enumerate.TransactionType;
import backend.atm.pojo.AuthCardRequest;
import backend.atm.pojo.AuthCardResponse;
import backend.atm.pojo.TransactionRequest;
import backend.atm.pojo.TransactionResponse;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class AtmServiceTest {

    @Autowired
    private  AtmService atmService;

    @Autowired
    private AccountRepo accountRepo;


    @Autowired
    private  CardRepo cardRepo;

    private final String cardNumber = "1111222233334444";
    private final String pinNumber = "1234";
    private final String accountNumber = "123456";
    private Long selectedCardPk;



    @BeforeEach
    public void init() {
        Card card = new Card();
        card.setCardNumber(cardNumber);
        card.setPinNumber(pinNumber);
        card.setFirstName("Gawun");
        card.setLastName("Kim");
        card = cardRepo.save(card);
        selectedCardPk = card.getPk();

        Account account1 = new Account();
        account1.setBalance(3000);
        account1.setAccountNumber(accountNumber);
        account1.setAccountName("DEFAULT");
        account1.setCardPk(card.getPk());
        account1.setStatus(AccountStatus.CHECKING);
        accountRepo.save(account1);

        Account account2 = new Account();
        account2.setBalance(30000);
        account2.setAccountNumber(accountNumber);
        account2.setAccountName("DEFAULT");
        account2.setCardPk(card.getPk());
        account2.setStatus(AccountStatus.SAVING);
        accountRepo.save(account2);
    }


    @Test
    public void authCard_Success() {
        AuthCardRequest request = new AuthCardRequest();
        request.setCardNumber(cardNumber);
        request.setPinNumber(pinNumber);
        AuthCardResponse response = atmService.authCard(request);
        assertEquals(response.getAuthCardStatus(), AuthCardStatus.APPROVED);
    }

    @Test
    public void authCard_Fail() {
        AuthCardRequest request = new AuthCardRequest();
        request.setCardNumber(cardNumber);
        request.setPinNumber("55555"); // incorrect PIN
        AuthCardResponse response = atmService.authCard(request);
        assertEquals(response.getAuthCardStatus(), AuthCardStatus.DECLINED);
    }

    @Test
    public void getAccount() {
        List<Account> list = atmService.getAccounts(selectedCardPk);
        Assertions.assertFalse(list.isEmpty());
    }

    @Test
    public void getAccount_noResult() {
        List<Account> list = atmService.getAccounts(selectedCardPk+ 1L);
        Assertions.assertTrue(list.isEmpty());
    }

    @Test
    public void createTransaction_depositMoney() {
        TransactionRequest transactionRequest = new TransactionRequest();

        transactionRequest.setAmount(2000);
        transactionRequest.setType(TransactionType.DEPOSIT);
        List<Account> beforeDeposit = accountRepo.findAllByCardPk(selectedCardPk);
        if(!beforeDeposit.isEmpty()) {
            Account pick = beforeDeposit.get(0);
            transactionRequest.setAccountPk(pick.getPk());
            int expectedBalance = pick.getBalance() + 2000; // 5000
            TransactionResponse transactionResponse = atmService.createTransaction(transactionRequest);
            assertEquals(expectedBalance, (int) transactionResponse.getRemainingBalance());
        }
    }

    @Test
    public void createTransaction_withdrawMoney() {
        TransactionRequest transactionRequest = new TransactionRequest();

        transactionRequest.setAmount(2000);
        transactionRequest.setType(TransactionType.WITHDRAW);
        List<Account> beforeDeposit = accountRepo.findAllByCardPk(selectedCardPk);
        if(!beforeDeposit.isEmpty()) {
            Account pick = beforeDeposit.get(0);
            transactionRequest.setAccountPk(pick.getPk());
            int expectedBalance = pick.getBalance() - 2000; // 1000
            TransactionResponse transactionResponse = atmService.createTransaction(transactionRequest);
            assertEquals(expectedBalance, (int) transactionResponse.getRemainingBalance());
        }
    }

    @Test
    public void createTransaction_notEnoughBalance() {
        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAmount(6000);
        transactionRequest.setType(TransactionType.WITHDRAW);
        List<Account> beforeDeposit = accountRepo.findAllByCardPk(selectedCardPk);
        if(!beforeDeposit.isEmpty()) {
            Account pick = beforeDeposit.get(0);
            transactionRequest.setAccountPk(pick.getPk());

            Throwable exception = assertThrows(RuntimeException.class, () -> atmService.createTransaction(transactionRequest));
            assertEquals("do not have enough balance.", exception.getMessage());

        }
    }
}
