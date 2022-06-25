package com.casino.dagacube.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="player")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Player {

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private Integer playerId;
    @Column
    private String userName;
    @Column
    private Double balance;

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
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
