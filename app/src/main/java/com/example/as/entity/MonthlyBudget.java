package com.example.as.entity;

import com.example.as.entity.Budget;
import com.example.as.entity.DailyBudget;

import java.util.ArrayList;
import java.util.Iterator;

public class MonthlyBudget extends Budget {

    private int day;
    private int month;
    private int year;
    int size;
    double amount;
    boolean ifOver;
    double balance;
    ArrayList<DailyBudget> dailyBudgetArray=new ArrayList<DailyBudget>();
    DailyBudget temp=new DailyBudget();

    public MonthlyBudget(){}//默认构造函数

    public MonthlyBudget(int year,int month,double amount){
        if(year%4==0&&year%100!=0){//闰年
            if(1<=month&&month<=12){
                this.month=month;
                this.setAmount(amount);
                switch(month){
                    case 1:day=31;break;
                    case 2:day=29;break;
                    case 3:day=31;break;
                    case 4:day=30;break;
                    case 5:day=31;break;
                    case 6:day=30;break;
                    case 7:day=31;break;
                    case 8:day=31;break;
                    case 9:day=30;break;
                    case 10:day=31;break;
                    case 11:day=30;break;
                    case 12:day=31;break;
                }
            }else{
                System.out.println("Wrong!");
            }
        }
        else{//非闰年
            if(1<=month&&month<=12){
                this.month=month;
                this.setAmount(amount);
                switch(month){
                    case 1:day=31;break;
                    case 2:day=28;break;
                    case 3:day=31;break;
                    case 4:day=30;break;
                    case 5:day=31;break;
                    case 6:day=30;break;
                    case 7:day=31;break;
                    case 8:day=31;break;
                    case 9:day=30;break;
                    case 10:day=31;break;
                    case 11:day=30;break;
                    case 12:day=31;break;
                }
            }else{
                System.out.println("Wrong!");
            }
        }
    }//构造函数

    public DailyBudget getBudgetByIndex(int index){
        return dailyBudgetArray.get(index);
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getday(){
        return day;
    }

    public int getSize(){
        return dailyBudgetArray.size();
    }

    public double getAmount(){
        return amount;
    }

    public boolean getIfOver(){
        return ifOver;
    }

    public double getBalance(){
        return balance;
    }

    public void setDailyBudgetArray(ArrayList<DailyBudget> dailyBudgetArray) {
        this.dailyBudgetArray = dailyBudgetArray;
    }


}
