package com.example.techno_store.MiniATM;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<User_Account, Long> {
    
    // Метод для поиска по номеру карты
    Optional<User_Account> findByCardNumber(int cardNumber);
}