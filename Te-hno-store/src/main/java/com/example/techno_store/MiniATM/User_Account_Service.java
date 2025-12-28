package com.example.techno_store.MiniATM;

import com.example.techno_store.Product.Product;
import com.example.techno_store.Product.ProductRepository;
import com.example.techno_store.Warehouse.Warehouse;
import com.example.techno_store.Warehouse.WarehouseService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@Slf4j
public class User_Account_Service {

    private final UserAccountRepository user_account_repository;
    private final ProductRepository product_repository;
    private final WarehouseService warehouseService;

    @Autowired
    public User_Account_Service(UserAccountRepository userAccountRepository,
                                ProductRepository productRepository,
                                WarehouseService warehouseService) {
        this.user_account_repository = userAccountRepository;
        this.product_repository = productRepository;
        this.warehouseService = warehouseService;
    }

    @Transactional
    public String purchase(String cardNumber, String pinCode, Long productId) {
        log.info("Начало покупки: карта={}, товар={}", cardNumber, productId);

        User_Account userAccount = user_account_repository.findByCardNumber(Integer.parseInt(cardNumber))
                .orElseThrow(() -> new IllegalArgumentException("Карта не найдена"));

        if (!pinCode.equals(String.valueOf(userAccount.getPin()))) {
            log.error("Неверный PIN для карты: {}", cardNumber);
            throw new RuntimeException("Неверный PIN");
        }

        Product product = product_repository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Товар не найден"));

        List<Warehouse> warehouses = warehouseService.getByProductId(productId);
        if (warehouses.isEmpty() || warehouses.get(0).getQuantity() <= 0) {
            log.error("Товара нет в наличии: {}", productId);
            throw new RuntimeException("Товара нет в наличии");
        }

        BigDecimal price = BigDecimal.valueOf(product.getPrice()); // Преобразуем Double в BigDecimal
        BigDecimal currentBalance = userAccount.getRubBalance();

        if (currentBalance.compareTo(price) < 0) {
            log.error("Недостаточно средств: баланс={}, цена={}", currentBalance, price);
            throw new RuntimeException("Недостаточно средств");
        }

        BigDecimal newBalance = currentBalance.subtract(price);
        userAccount.setRubBalance(newBalance);
        user_account_repository.save(userAccount);

        warehouseService.decreaseQuantity(productId, 1);

        log.info("Покупка успешна: карта={}, товар={}, цена={}, новый баланс={}",
                cardNumber, product.getName(), price, newBalance);

        return String.format("Успешно! Куплен: %s за %s ₽. Баланс: %s ₽",
                product.getName(), price, newBalance);
    }

    public BigDecimal checkBalance(String cardNumber, String pinCode) {
        log.info("Проверка баланса: карта={}", cardNumber);
        User_Account userAccount = user_account_repository.findByCardNumber(Integer.parseInt(cardNumber))
                .orElseThrow(()-> new IllegalArgumentException("Карта не найдена"));
        if(pinCode.equals(String.valueOf((userAccount.getPin())))){
            log.info("Баланс: {}", userAccount.getRubBalance());
            throw new RuntimeException("Неверный PIN");
        }
        log.info("Баланс карты {}: {} ₽", cardNumber, userAccount.getRubBalance());
        return userAccount.getRubBalance();
    }
    @Transactional
    public String deposit(String cardNumber, BigDecimal amount, String pinCode) {
        log.info("Начало пополнения: карта={}, сумма={}", cardNumber, amount);
        User_Account userAccount = user_account_repository.findByCardNumber(Integer.parseInt(cardNumber))
                .orElseThrow(() -> new IllegalArgumentException("Карта не найдена"));

        if(!pinCode.equals(String.valueOf((userAccount.getPin())))){
        throw new RuntimeException("Неверный PIN");
        }
        BigDecimal newBalanse = userAccount.getRubBalance().add(amount);
        userAccount.setRubBalance(newBalanse);
        user_account_repository.save(userAccount);
        log.info("Пополнение успешно: карта={}, добавлено={}, новый баланс={}",
                cardNumber, amount, newBalanse);

        return String.format("Успешно! Зачислено: %s ₽. Новый баланс: %s ₽",
                amount, newBalanse);
    }
}