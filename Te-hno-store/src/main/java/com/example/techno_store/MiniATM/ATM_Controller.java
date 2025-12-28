package com.example.techno_store.MiniATM;
import java.math.BigDecimal;

import com.example.techno_store.MiniATM.Bank.Bank;
import com.example.techno_store.MiniATM.Bank.BankRepository;
import com.example.techno_store.MiniATM.Bank.BankService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequestMapping("/api/ATM")

public class ATM_Controller {
    private final User_Account_Service userAccountService;
    private final BankService bankService;

    private final BankRepository bankRepository;

    public ATM_Controller(User_Account_Service userAccountService, BankService bankService,
                          BankRepository bankRepository) {
        this.userAccountService = userAccountService;
        this.bankService = bankService;
        this.bankRepository = bankRepository;
    }

    @PostMapping("/bank/deposit")
    public ResponseEntity<String> depositToBank(@RequestParam BigDecimal amount) {
        try {
            String result = bankService.depositToBank(amount);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка: " + e.getMessage());
        }
    }
    @GetMapping("/bank/balance")
    public ResponseEntity<BigDecimal> checkBankBalance() {
        log.info("Проверка баланса банка");
        Bank bank=bankRepository.findById(1L)
                .orElseThrow(()->new RuntimeException("Bank not found"));
        return ResponseEntity.ok(bank.getRubBalance());
    }

    @GetMapping("/balance")
    public ResponseEntity<String> checkBalance(@RequestParam String cardNumber, @RequestParam String pinCode) {
        try {
            BigDecimal balance = userAccountService.checkBalance(cardNumber, pinCode);
            return ResponseEntity.ok(balance.toString());
        } catch (Exception e) {
            log.error("Error checking balance: {}", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Ошибка: " + e.getMessage());
        }
    }
    @PostMapping("/purchase/from-cart")
    public ResponseEntity<String> purchaseFromCart(
            @RequestParam String cardNumber,
            @RequestParam String pinCode,
            @RequestParam Long productId
    ){
        try {
            String result =userAccountService.purchase(cardNumber, pinCode, productId);
            return ResponseEntity.ok(result);
        }catch (Exception e){
            log.error("Error during purchase: {}", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Ошибка покупки: " + e.getMessage());
        }
    }
    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(
            @RequestParam String cardNumber,
            @RequestParam BigDecimal amount,
            @RequestParam String pinCode) {
        log.info("Пополнение счета: карта={}, сумма={}", cardNumber, amount);
        try {
            String result = userAccountService.deposit(cardNumber, amount, pinCode);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Error during deposit: {}", e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Ошибка пополнения: " + e.getMessage());
        }
    }

}
