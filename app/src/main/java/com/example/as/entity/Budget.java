package com.example.as.entity;

import java.sql.Date;

public class Budget {
    private Integer id;
    private double amount;
    private boolean ifOver;
    private double balance;
    private Date date;
    private String accountId;

    //默认构造方法
    public Budget(){id = null;}

    public Budget(double amount, Date date, double balance, String accountId){
        this.id = null;
        this.amount = amount;
        judgeIfOver();
        this.date = date;
        this.balance = balance;
        this.accountId = accountId;
    }

    public Budget(Integer id, double amount, Date date, double balance, String accountId){
        this.id = id;
        this.amount = amount;
        judgeIfOver();
        this.date = date;
        this.balance = balance;
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

    public boolean getIfOver() {
        return ifOver;
    }

    public void setIfOver(boolean ifOver) {
        this.ifOver = ifOver;
    }

    public Date getDate(){return date;}

    public void setDate(Date date){this.date = date;}

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        judgeIfOver();
        this.balance = balance;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void judgeIfOver(){
        if(balance < 0)
            setIfOver(false);
        else
            setIfOver(true);
    }
}
