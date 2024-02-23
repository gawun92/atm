package backend.atm.db.repo;

import backend.atm.db.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepo extends JpaRepository<Card, Long> {

    Card findByPk(Long pk);
    @Query(name = "SELECT * FROM public.card WHERE card_number = :cardNumber ")
    Card searchByCardNumber(String cardNumber);
}
