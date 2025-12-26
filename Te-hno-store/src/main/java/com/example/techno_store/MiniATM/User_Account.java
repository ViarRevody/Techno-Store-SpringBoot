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
@Table(name = "user_account")
public class User_Account {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

}
