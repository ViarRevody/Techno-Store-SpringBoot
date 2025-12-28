package com.example.techno_store.MiniATM;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<User_Account, Long> {

    @Query("SELECT u FROM User_Account u WHERE u.card_number = :cardNumber")
    Optional<User_Account> findByCardNumber(@Param("cardNumber") int cardNumber);
}