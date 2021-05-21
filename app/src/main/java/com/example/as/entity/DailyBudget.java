package com.example.as.entity;

import java.util.Date;

public class DailyBudget extends Budget {


    private String id;
    private double amount;
    private boolean ifOver;
    private double balance;
    private int year,month,day;
    private String accountId;
    //private int day;
    public static int billcount=0;

    public DailyBudget(){
        billcount++;
        id=Integer.toString(billcount);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public double getAmount() {
        return amount;
    }

    @Override
    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public boolean getIfOver() {
        return ifOver;
    }

    @Override
    public void setIfOver(boolean ifOver) {
        this.ifOver = ifOver;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public String getAccountId() {
        return accountId;
    }

    @Override
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Date getDate(){
        Date date = new Date();
        date.setYear(year);
        date.setMonth(month-1);
        date.setDate(day);
        return date;
    }

}
