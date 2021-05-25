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
    private Bill cursor = null;



    public void setDailyBillArray(ArrayList<Bill> dailyBillArray) {
        this.dailyBillArray = dailyBillArray;
        double tempAmount = 0;
        for(int i = 0;i < dailyBillArray.size();i++){
            tempAmount += dailyBillArray.get(i).getAmount();
        }
        dailyAmount = tempAmount;
        date.setYear(dailyBillArray.get(0).getDate().getYear());
        date.setMonth(dailyBillArray.get(0).getDate().getMonth());
        date.setDate(dailyBillArray.get(0).getDate().getDate());
    }

    public Bill getBillByIndex(int index){
        cursor = null;
        if(index >= 0&&index < getSize())
            cursor = dailyBillArray.get(index);
        return cursor;
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
