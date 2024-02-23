package backend.atm.db.repo;

import backend.atm.db.entity.Card;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.Assertions;

@SpringBootTest
@Transactional
public class CardRepoTest {

    @Autowired private CardRepo cardRepo;

    @Test
    public void saveAndSearch() {
        Card card = new Card();
        card.setFirstName("Gawun");
        card.setLastName("Kim");
        card.setPinNumber("123");
        card.setCardNumber("1234123412341234");
        cardRepo.save(card);

        Assertions.assertNotNull(cardRepo.searchByCardNumber("1234123412341234"));
    }
}
