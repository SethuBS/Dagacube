package com.casino.dagacube.model;

import javax.persistence.*;

@Entity
@Table(name="player")
public class Player {

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private java.lang.Integer playerId;
    @Column
    private String userName;
    @Column
    private Double balance;

    public java.lang.Integer getPlayerId() {
        return playerId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
