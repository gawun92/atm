package backend.atm.db.entity;

import backend.atm.enumerate.AccountStatus;
import jakarta.persistence.*;

@Entity
public class Account extends SuperEntity{
    private Long cardPk = 0L;
    private String accountName;
    private String accountNumber;
    @Enumerated(EnumType.STRING)
    private AccountStatus status = AccountStatus.CHECKING;
    private Integer balance = 0;
    private String test = "";




    public Long getCardPk() {
        return cardPk;
    }

    public void setCardPk(Long cardPk) {
        this.cardPk = cardPk;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Long getPk() {
        return this.pk;
    }
    public void setPk(Long pk) {
        this.pk = pk;
    }

}
