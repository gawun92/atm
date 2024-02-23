package backend.atm.service;

import backend.atm.db.entity.Account;
import backend.atm.db.entity.Card;
import backend.atm.db.entity.Transaction;
import backend.atm.db.repo.AccountRepo;
import backend.atm.db.repo.CardRepo;
import backend.atm.db.repo.TransactionRepo;
import backend.atm.enumerate.AuthCardStatus;
import backend.atm.enumerate.TransactionType;
import backend.atm.pojo.AuthCardRequest;
import backend.atm.pojo.AuthCardResponse;
import backend.atm.pojo.TransactionRequest;
import backend.atm.pojo.TransactionResponse;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AtmService {
    private final CardRepo cardRepo;
    private final AccountRepo accountRepo;
    private final TransactionRepo transactionRepo;

    @Autowired
    public AtmService(CardRepo cardRepo, AccountRepo accountRepo, TransactionRepo transactionRepo) {
        this.cardRepo = cardRepo;
        this.accountRepo = accountRepo;
        this.transactionRepo = transactionRepo;
    }


    public AuthCardResponse authCard(AuthCardRequest request) {
        AuthCardResponse response = new AuthCardResponse();

        if(StringUtils.isBlank(request.getCardNumber()) || request.getCardNumber().length()!=16) {
            response.setMessage("invalid card number.");
            return response;
        }
        if(StringUtils.isBlank(request.getPinNumber())) {
            response.setMessage("no pin number.");
            return response;
        }

        Card card = cardRepo.searchByCardNumber(request.getCardNumber());
        if(card==null) {
            response.setMessage("no card.");
            return response;
        }
        if(!card.getPinNumber().equals(request.getPinNumber())) {
            response.setMessage("incorrect pin number.");
            response.setAuthCardStatus(AuthCardStatus.DECLINED);
            return response;
        }
        response.setCardPk(card.getPk());
        response.setAuthCardStatus(AuthCardStatus.APPROVED);
        return response;
    }

    public List<Account> getAccounts(Long cardPk) {
        return accountRepo.findAllByCardPk(cardPk);
    }

    public TransactionResponse createTransaction(TransactionRequest request) {
        if(request.getAccountPk()==0L)
            throw new RuntimeException("no account pk.");
        if(request.getType()==null)
            throw new RuntimeException("no transaction action.");
        if(request.getAmount()==null || request.getAmount() <= 0)
            throw new RuntimeException("invalid transaction amount.");
        Account account = accountRepo.findByPk(request.getAccountPk());
        if(account==null)
            throw new RuntimeException("no account.");


        Transaction transaction = new Transaction();
        transaction.setTransactionType(request.getType());
        transaction.setAmount(request.getAmount());
        transaction.setAccountPk(request.getAccountPk());
        transactionRepo.save(transaction);

        if(request.getType().equals(TransactionType.DEPOSIT)) {
            account.setBalance(account.getBalance()+ request.getAmount());
            accountRepo.save(account);
        }
        else if(request.getType().equals(TransactionType.WITHDRAW)) {
            if(account.getBalance() < request.getAmount())
                throw new RuntimeException("do not have enough balance.");
            account.setBalance(account.getBalance() - request.getAmount());
            accountRepo.save(account);
        }
        TransactionResponse response = new TransactionResponse();
        response.setAccountName(account.getAccountName());
        response.setAccountNumber(account.getAccountNumber());
        response.setRemainingBalance(account.getBalance());
        response.setTransactionType(transaction.getTransactionType());
        response.setTransactionAmount(transaction.getAmount());
        return response;
    }

}
