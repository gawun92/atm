package backend.atm.controller;

import backend.atm.db.entity.Account;
import backend.atm.db.entity.Card;
import backend.atm.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/admin")
public class AdminController {

    private final AdminService adminService;


    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @PostMapping(value = "/createCard")
    public Card createCard(@RequestBody Card card) {
        return adminService.createCard(card);
    }

    @PostMapping(value = "/createAccount")
    public Account createAccount(@RequestBody Account account) {
        return adminService.createAccount(account);
    }
}
