package com.casino.dagacube.model;

import javax.persistence.*;

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private Integer transactionId;
    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player playerId;
    @Column
    private Double amount;
    @Column
    private TransactionType transactionType;

    public Integer getTransactionId() {
        return transactionId;
    }

    public Player getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Player playerId) {
        this.playerId = playerId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", amount=" + amount +
                ", transactionType=" + transactionType +
                '}';
    }
}
