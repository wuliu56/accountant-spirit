package com.example.as.service;

import com.example.as.entity.Budget;
import com.example.as.entity.DailyBudget;

import java.util.ArrayList;
import java.util.Iterator;

public class MonthlyBudget extends Budget {

    private int day;
    private int month;
    private int year;

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

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public DailyBudget getDailyBudget(int day){
        Iterator<DailyBudget> it=dailyBudgetArray.iterator();
        while(it.hasNext()){
            temp=it.next();
            if(temp.getDay()==day){
                return temp;
            }
        }
        return null;
    }

    public double getMonthlyAmount(){
        return this.getAmount();
    }

    public void setMonthlyAmount(double amount){
        this.setAmount(amount);
    }

    public boolean getMonthlyIfOver(){
        return this.getIfOver();
    }

    public void setMonthlyIfOver(boolean ifOver){
        this.setIfOver(ifOver);
    }

}
