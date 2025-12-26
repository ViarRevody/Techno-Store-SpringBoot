package com.example.techno_store.MiniATM;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user_")
public class User {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

}
