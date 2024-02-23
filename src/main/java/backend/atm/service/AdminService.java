package backend.atm.service;

import backend.atm.db.entity.Account;
import backend.atm.db.entity.Card;
import backend.atm.db.repo.AccountRepo;
import backend.atm.db.repo.CardRepo;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private final CardRepo cardRepo;
    private final AccountRepo accountRepo;

    @Autowired
    public AdminService(CardRepo cardRepo, AccountRepo accountRepo) {
        this.cardRepo = cardRepo;
        this.accountRepo = accountRepo;
    }

    public Card createCard(Card card) {
        if (StringUtils.isBlank(card.getCardNumber()) || card.getCardNumber().length() != 16)
            throw new RuntimeException("invalid card number.");
        if (StringUtils.isBlank(card.getPinNumber()))
            throw new RuntimeException("no pin number.");
        if (StringUtils.isBlank(card.getFirstName()) || StringUtils.isBlank(card.getLastName()))
            throw new RuntimeException("no name.");
        Card temp = cardRepo.searchByCardNumber(card.getCardNumber());
        if (temp != null) {
            throw new RuntimeException("duplicate card number exists.");
        }
        return cardRepo.save(card);
    }

    public Account createAccount(Account account) {
        if(account.getCardPk()==0L)
            throw new RuntimeException("no connection to card.");
        if(StringUtils.isBlank(account.getAccountName()) || StringUtils.isBlank(account.getAccountNumber()))
            throw new RuntimeException("no account name or number.");
        if(account.getBalance() < 0)
            throw new RuntimeException("balance cannot be negative value.");
        Card card = cardRepo.findByPk(account.getCardPk());
        if(card==null)
            throw new RuntimeException("no card.");
        Account tempAccount = accountRepo.findByAccountNumber(account.getAccountNumber());
        if(tempAccount!=null)
            throw new RuntimeException("duplicate account number.");

        return accountRepo.save(account);
    }
}
