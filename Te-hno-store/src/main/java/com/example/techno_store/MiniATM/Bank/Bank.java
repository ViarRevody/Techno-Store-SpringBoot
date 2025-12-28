package com.example.techno_store.MiniATM.Bank;

import jakarta.persistence.*;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "bank")
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id=1L;
    
    @Column(name = "rub_balance", nullable = false, precision = 19, scale = 2)
    private BigDecimal rubBalance = BigDecimal.ZERO;
    
    @Column(name = "usd_balance", nullable = false, precision = 19, scale = 2)
    private BigDecimal usdBalance = BigDecimal.ZERO;
}