package com.example.as.entity;

import java.sql.Date;

public class Bill {

    private Integer id;
    private double amount;
    private Date date;
    private Type type;
    private Wallet wallet;
    private String accountId;

    //默认构造方法
    public Bill(){
        this.id = null;
    }

    //用于向数据库插入时构造
    public Bill(double amount, Date date, Type type, Wallet wallet, String accountId){
        this.id = null;
        this.amount = amount;
        this.date = date;
        this.type = type;
        this.wallet = wallet;
        this.accountId = accountId;
    }

    //用于从数据库查询时构造
    public Bill(Integer id, double amount, Date date, Type type, Wallet wallet, String accountId){
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.type = type;
        this.wallet = wallet;
        this.accountId = accountId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

}
