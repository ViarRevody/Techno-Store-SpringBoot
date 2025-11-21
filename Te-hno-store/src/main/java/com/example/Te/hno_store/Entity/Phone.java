package com.example.Te.hno_store.Entity;

import com.example.Te.hno_store.Product.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Phone extends Product {

    @Column(name = "ram")
    private int ram;

    @Column(name = "battary")
    private int battary;

    @Column(name = "ScreenHertz")
    private int ScreenHertz;

}