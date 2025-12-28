package com.example.techno_store.MiniATM.Bank;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class BankService {
    private final BankRepository bankRepository;

    @Transactional
    public String depositToBank(BigDecimal amount) {
        Bank bank = bankRepository.findById(1L).orElseThrow();
        bank.setRubBalance(bank.getRubBalance().add(amount));
        bankRepository.save(bank);
        return "Банк пополнен на " + amount + " ₽. Баланс: " + bank.getRubBalance() + " ₽";
    }
}