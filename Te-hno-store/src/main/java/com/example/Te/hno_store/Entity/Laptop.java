package com.example.Te.hno_store.Entity;

import com.example.Te.hno_store.Product.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Laptop extends Product {
   @Column(name = "processor")
    private String processor;

    @Column(name = "ram")
    private int ram;

    @Column(name = "graphicscard")
    private String graphicscard;

}