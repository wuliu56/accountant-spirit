package com.example.as.entity;

import com.example.as.entity.Bill;
import com.example.as.entity.Currency;
import com.example.as.entity.Type;
import com.example.as.entity.Wallet;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;

public class DailyBill {



    private ArrayList<Bill> dailyBillArray=new ArrayList<Bill>();
    private Date date;
    private double dailyAmount;
    private int size;

    public void setDailyBillArray(ArrayList<Bill> dailyBillArray) {
        this.dailyBillArray = dailyBillArray;
    }

    public Bill getBillByIndex(int index){
        return dailyBillArray.get(index);
    }

    public Date getDate() {
        return date;
    }

    public double getDailyAmount() {
        return dailyAmount;
    }

    public int getSize(){
        return dailyBillArray.size();
    }










}
