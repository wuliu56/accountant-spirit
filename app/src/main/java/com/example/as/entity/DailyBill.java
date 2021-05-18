package com.example.as.entity;

import com.example.as.entity.Bill;
import com.example.as.entity.Currency;
import com.example.as.entity.Type;
import com.example.as.entity.Wallet;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;

public class DailyBill {

    private ArrayList<Bill> dailyBill=new ArrayList<Bill>();
    private Date date;
    private double dailyAmount;
    private ArrayList<Bill> targets=new ArrayList<Bill>();
    private Bill temp=new Bill();

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public void newBill(double amount, Date date, Type type, Wallet wallet, Currency currency){
        Bill bill=new Bill();
        bill.setAmount(amount);
        bill.setDate(date);
        bill.setType(type);
        bill.setWallet(wallet);
        bill.setCurrency(currency);
        dailyBill.add(bill);
    }

    public void deleteBill(int id){
        dailyBill.remove(id);
    }

    public int getSize(){
        return dailyBill.size();
    }

    public Bill queryBillById(int id){
        Iterator<Bill> it=dailyBill.iterator();
        while(it.hasNext()){
            temp=it.next();
            if(temp.getId()==id){
                return temp;
            }
        }
        return null;
    }

    public ArrayList<Bill> queryBillsByType(Type type){
        Iterator<Bill> it=dailyBill.iterator();
        while(it.hasNext()){
            temp=it.next();
            if(temp.getType().equals(type)){
                targets.add(temp);
            }
        }
        return targets;
    }

    public double getDailyAmount() {
        Iterator<Bill> it=dailyBill.iterator();
        while(it.hasNext()) {
            temp = it.next();
            dailyAmount += temp.getAmount();
        }
        return dailyAmount;
    }

    public void setDailyAmount(double dailyAmount) {
        this.dailyAmount = dailyAmount;
    }

    public double getAmountByType(Type type) {
        Iterator<Bill> it=dailyBill.iterator();
        int amount=0;
        while(it.hasNext()) {
            temp = it.next();
            if(temp.getType().equals(type))
                amount += temp.getAmount();
        }
        return amount;
    }

}
