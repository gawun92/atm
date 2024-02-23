package backend.atm.db.entity;

import backend.atm.enumerate.TransactionType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Entity
public class Transaction extends SuperEntity{
    private Long accountPk;
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
    private Integer amount;




    public Long getAccountPk() {
        return accountPk;
    }

    public void setAccountPk(Long accountPk) {
        this.accountPk = accountPk;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
