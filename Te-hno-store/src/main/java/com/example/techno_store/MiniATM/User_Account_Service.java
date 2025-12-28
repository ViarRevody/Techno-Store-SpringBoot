package com.example.techno_store.MiniATM;

import com.example.techno_store.Cart.Cart;
import com.example.techno_store.Cart.CartItem;
import com.example.techno_store.Cart.CartItemRepository;
import com.example.techno_store.Cart.CartRepository;
import com.example.techno_store.MiniATM.Bank.Bank;
import com.example.techno_store.MiniATM.Bank.BankRepository;
import com.example.techno_store.Product.Product;
import com.example.techno_store.Product.ProductRepository;
import com.example.techno_store.Warehouse.Warehouse;
import com.example.techno_store.Warehouse.WarehouseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class User_Account_Service {

    private final UserAccountRepository user_account_repository;
    private final ProductRepository product_repository;
    private final WarehouseService warehouseService;

    private final BankRepository bankRepository;
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;

    @Autowired
    public User_Account_Service(UserAccountRepository userAccountRepository,
                                ProductRepository productRepository,
                                WarehouseService warehouseService,
                                BankRepository bankRepository, CartItemRepository cartItemRepository, CartRepository cartRepository) {
        this.user_account_repository = userAccountRepository;
        this.product_repository = productRepository;
        this.warehouseService = warehouseService;
        this.bankRepository = bankRepository;
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
    }

    @Transactional
    public String purchase(String cardNumber, String pinCode, Long productId) {
        log.info("Начало покупки: карта={}, товар={}", cardNumber, productId);

        User_Account userAccount = user_account_repository.findByCardNumber(Integer.parseInt(cardNumber))
                .orElseThrow(() -> new IllegalArgumentException("Карта не найдена"));
        if(!pinCode.equals(String.valueOf(((userAccount.getPin()))))){
            throw new RuntimeException("Неверный PIN");
        }
        User user = userAccount.getUser();
        if(user ==null){
            throw new RuntimeException("Пользователь не найден");
        }

        boolean inCart = false;
        CartItem cartItem = null;

        if (user.getCart() != null && user.getCart().getItems() != null) {
            Optional<CartItem> itemOptional = user.getCart().getItems().stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .findFirst();

            if (itemOptional.isPresent()) {
                inCart = true;
                cartItem = itemOptional.get();
                log.info("Товар найден в корзине: {}", cartItem.getProduct().getName());
            }
        }

        if (inCart && cartItem != null) {
            return processPurchaseFromCart(userAccount, cartItem);
        }
        Product product = product_repository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Товар не найден"));

        throw new RuntimeException(
                String.format("Товар '%s' не найден в вашей корзине. " +
                                "Сначала добавьте его в корзину через /api/cart/add",
                        product.getName())
        );
    }
    private String processPurchaseFromCart(User_Account userAccount, CartItem cartItem) {
        Product product = cartItem.getProduct();
        int quantity = cartItem.getQuantity();
        BigDecimal price = BigDecimal.valueOf(product.getPrice());
        BigDecimal totalAmount = price.multiply(BigDecimal.valueOf(quantity));

        BigDecimal currentBalance = userAccount.getRubBalance();
        if (currentBalance.compareTo(totalAmount) < 0) {
            throw new RuntimeException("Недостаточно средств");
        }

        BigDecimal newBalance = currentBalance.subtract(totalAmount);
        userAccount.setRubBalance(newBalance);
        user_account_repository.save(userAccount);

        Bank bank = bankRepository.findById(1L).orElseThrow();
        BigDecimal newBankBalance = bank.getRubBalance().add(totalAmount);
        bank.setRubBalance(newBankBalance);
        bankRepository.save(bank);

        Cart cart = cartItem.getCart();
        cart.getItems().remove(cartItem);
        cartRepository.save(cart);

        log.info("Покупка из корзины успешна: {}", product.getName());

        return String.format(" Куплено из корзины: %s x%d за %s ₽. Ваш баланс: %s ₽",
                product.getName(), quantity, totalAmount, newBalance);
    }
    public BigDecimal checkBalance(String cardNumber, String pinCode) {
        log.info("Проверка баланса: карта={}", cardNumber);
        User_Account userAccount = user_account_repository.findByCardNumber(Integer.parseInt(cardNumber))
                .orElseThrow(()-> new IllegalArgumentException("Карта не найдена"));
        if(!pinCode.equals(String.valueOf((userAccount.getPin())))){
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