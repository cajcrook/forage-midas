package com.jpmc.midascore.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class UserRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal balance;

    @Transient  // This field won't be stored in the database
    private float value;

    protected UserRecord() {
    }

    public UserRecord(String name, BigDecimal balance) {
        this.name = name;
        this.balance = balance;
        this.value = balance.floatValue(); // Set value based on balance
    }

    public UserRecord(String name, float value) {
        this.name = name;
        this.value = value;
        this.balance = BigDecimal.valueOf(value); // Ensure consistency
    }

    @Override
    public String toString() {
        return String.format("User[id=%d, name='%s', balance='%.2f', value='%.2f']", id, name, balance, value);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
        this.value = balance.floatValue(); // Keep both fields in sync
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
        this.balance = BigDecimal.valueOf(value); // Keep both fields in sync
    }
}
