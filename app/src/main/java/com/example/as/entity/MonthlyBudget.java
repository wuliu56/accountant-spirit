package com.example.as.entity;

import com.example.as.entity.Budget;
import com.example.as.entity.DailyBudget;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

public class MonthlyBudget extends Budget {

    ArrayList<DailyBudget> dailyBudgetArray=new ArrayList<DailyBudget>();
    DailyBudget cursor = null;

    public MonthlyBudget(ArrayList<DailyBudget> dailyBudgetArray){
        this.dailyBudgetArray = dailyBudgetArray;
        double tempBalance = 0;
        double tempAmount = 0;
        for(int i = 0;i < getSize();i++){
            tempAmount += dailyBudgetArray.get(i).getAmount();
            tempBalance += dailyBudgetArray.get(i).getBalance();
        }
        setAmount(tempAmount);
        setBalance(tempBalance);
        Date date = getDate();
        date.setYear(dailyBudgetArray.get(0).getYear()-1900);
        date.setMonth(dailyBudgetArray.get(0).getMonth()-1);
        setDate(date);
        judgeIfOver();
    }

    public DailyBudget getBudgetByIndex(int index){
        cursor = null;
        if(index >= 0&&index < getSize())
            cursor = dailyBudgetArray.get(index);
        return cursor;
    }

    //实际月份
    public int getMonth() {
        return getDate().getMonth() + 1;
    }

    public int getYear() {
        return getDate().getYear() + 1900;
    }

    public int getSize(){
        return dailyBudgetArray.size();
    }

}
