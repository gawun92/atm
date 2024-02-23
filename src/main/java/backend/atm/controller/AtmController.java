package backend.atm.controller;

import backend.atm.db.entity.Account;
import backend.atm.pojo.AuthCardRequest;
import backend.atm.pojo.AuthCardResponse;
import backend.atm.pojo.TransactionRequest;
import backend.atm.pojo.TransactionResponse;
import backend.atm.service.AtmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/atm")
public class AtmController {
    private final AtmService atmService;
    @Autowired
    public AtmController(AtmService atmService) {
        this.atmService = atmService;
    }

    @PostMapping(value = "/authCard")
    public AuthCardResponse authCard(@RequestBody AuthCardRequest request) {
        return atmService.authCard(request);
    }

    @GetMapping(value = "/getAccounts/{cardPk}")
    public List<Account> getAccounts(@PathVariable("cardPk") Long cardPk) {
        return atmService.getAccounts(cardPk);
    }

    @PostMapping(value = "/createTransaction")
    public TransactionResponse createTransaction(@RequestBody TransactionRequest request) {
        return atmService.createTransaction(request);
    }
}
