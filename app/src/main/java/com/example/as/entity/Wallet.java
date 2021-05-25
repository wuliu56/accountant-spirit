package com.example.as.entity;

public class Wallet {


    private String id;
    private String name;
    private double amount;
    private String accountId;

    public Wallet(){}

    public Wallet(String id, String name, double amount, String accountId){
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.accountId = accountId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount=amount;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }


}
