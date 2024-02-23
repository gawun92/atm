package backend.atm.db.repo;

import backend.atm.db.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {
    @Query(name = "SELECT * FROM public.account WHERE account_number = :accountNumber ")
    Account findByAccountNumber(String accountNumber);

    @Query(name = "SELECT * FROM public.account WHERE card_pk = :cardPk ")
    List<Account> findAllByCardPk(Long cardPk);

    Account findByPk(Long pk);
}
