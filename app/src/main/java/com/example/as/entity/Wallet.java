package com.example.as.entity;

public class Wallet {

    private String name;
    private double amount;

    public Wallet(){};

    public Wallet(String name,double amount){
        this.name=name;
        this.amount=amount;
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

}
