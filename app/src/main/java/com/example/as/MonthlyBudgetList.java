package com.example.as;

import java.util.ArrayList;
import java.util.Iterator;

public class MonthlyBudgetList {

    MonthlyBudget temp=new MonthlyBudget();

    ArrayList<MonthlyBudget> monthlyBudgetArray=new ArrayList<MonthlyBudget>();

    public MonthlyBudget getMonthlyBudget(int year,int month){
        //遍历整个数组
        Iterator<MonthlyBudget> it=monthlyBudgetArray.iterator();
        while(it.hasNext()){
            temp=it.next();
            if(temp.getYear()==year&&temp.getMonth()==month){
                return temp;
            }
        }
        return null;
    }

    public void newMonthlyBudget(int year,int month,double amount){
        monthlyBudgetArray.add(new MonthlyBudget(year,month,amount));
    }

    public void deleteMonthlyBudget(int year,int month){
        Iterator<MonthlyBudget> it=monthlyBudgetArray.iterator();
        while(it.hasNext()){
            temp=it.next();
            if(temp.getYear()==year&&temp.getMonth()==month){
                int index=monthlyBudgetArray.indexOf(temp);
                monthlyBudgetArray.remove(index);
                break;//能直接删吗
            }
        }
    }
}
