package com.example.as.entity;

public class Wallet {


    private Integer id;
    private String name;
    private double amount;
    private String accountId;

    //默认构造方法
    public Wallet(){
        this.id = null;
    }

    //用于向数据库插入时构造
    public Wallet (String name, double amount, String accountId){
        this.id = null;
        this.name = name;
        this.amount = amount;
        this.accountId = accountId;
    }

    //用于向数据库查询时构造
    public Wallet (Integer id, String name, double amount, String accountId){
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.accountId = accountId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
        this.amount = amount;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

}
