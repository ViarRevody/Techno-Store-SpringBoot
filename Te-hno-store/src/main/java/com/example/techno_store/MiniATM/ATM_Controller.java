package com.example.techno_store.MiniATM;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequestMapping("/api/ATM")
@RequiredArgsConstructor
public class ATM_Controller {
    private final User_Account_Service userAccountService;

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

    @PostMapping("/purchase")
    public ResponseEntity<String> purchase(
            @RequestParam String cardNumber,
            @RequestParam String pinCode,
            @RequestParam Long productId) {
        log.info("Покупка товара: карта={}, товар={}", cardNumber, productId);
        try {
            String result = userAccountService.purchase(cardNumber, pinCode, productId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
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
