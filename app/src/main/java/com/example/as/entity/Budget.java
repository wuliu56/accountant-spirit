package com.example.as.entity;

public class Budget {
    private String id;
    private double amount;
    private boolean ifOver;
    private double balance;
    private String accountId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean getIfOver() {
        return ifOver;
    }

    public void setIfOver(boolean ifOver) {
        this.ifOver = ifOver;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }






}
