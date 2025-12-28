package com.example.techno_store.MiniATM;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "user_account")
public class User_Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "card_number", nullable = false)
    private int card_number;

    @Column(name = "pin", nullable = false)
    private int pin;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "rub_balance", nullable = false, precision = 19, scale = 2)
    private BigDecimal rubBalance = BigDecimal.ZERO;

    @Column(name = "usd_balance", nullable = false, precision = 19, scale = 2)
    private BigDecimal usdBalance = BigDecimal.ZERO;
}
