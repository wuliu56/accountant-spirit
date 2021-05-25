package com.example.as.service;

import com.example.as.dao.impl.BillDaoImpl;
import com.example.as.dao.impl.DailyBudgetDaoImpl;
import com.example.as.entity.Bill;
import com.example.as.entity.DailyBudget;
import com.example.as.entity.MonthlyBudget;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

public class BillRecorder {

    private String accountId;
    BillDaoImpl billdaoimpl;
    DailyBudgetDaoImpl dailybudgetdaoimpl;

    public void newDailyBudget(DailyBudget dailybudget){
        dailybudgetdaoimpl.insert(dailybudget);
    }

    public void deleteDailyBudget(Date date){
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        dailybudgetdaoimpl.deleteByDate(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH),accountId);
    }

    public void deleteMonthlyBudget(int year,int month){
        ArrayList<DailyBudget> dailybudgetarray=dailybudgetdaoimpl.findByAccountId(accountId);
        DailyBudget tempdailybudget = new DailyBudget();
        Iterator<DailyBudget>it=dailybudgetarray.iterator();
        while(it.hasNext()){
            tempdailybudget=it.next();
            if(tempdailybudget.getYear()==year&&tempdailybudget.getMonth()==month){
                dailybudgetdaoimpl.deleteByDate(tempdailybudget.getYear(),tempdailybudget.getMonth(),tempdailybudget.getDay(),accountId);
            }//笨方法
        }
    }

    public void setMonthlyBudget(int year,int month,double amount){
        MonthlyBudget monthlybudget=new MonthlyBudget(dailybudgetdaoimpl.findByMonth(year,month,accountId));
        int day=monthlybudget.getSize();
        double dailyamount=amount/day;
        for(int i=1;i<=day;i++){
            DailyBudget tempdailybudget=new DailyBudget();
            tempdailybudget.setAmount(dailyamount);
            tempdailybudget.setIfOver(false);
            tempdailybudget.setBalance(dailyamount);
            tempdailybudget.setYear(year);
            tempdailybudget.setMonth(month);
            tempdailybudget.setDay(i);
            tempdailybudget.setAccountId(accountId);
            dailybudgetdaoimpl.insert(tempdailybudget);
        }

    }

    public DailyBudget queryDailyBudget(int year,int month, int day){
        return dailybudgetdaoimpl.findByDay(year,month,day,accountId);
    }

    public MonthlyBudget queryMonthlyBudget(int year, int month){
        ArrayList<DailyBudget> dailybudgetarray=dailybudgetdaoimpl.findByMonth(year,month,accountId);
        MonthlyBudget monthlybudget=new MonthlyBudget(dailybudgetarray);
        return monthlybudget;
    }


    public void newBill(Bill bill){
        billdaoimpl.insert(bill);
    }

    public void deleteBill(Integer id){
        billdaoimpl.deleteByBillId(id);
    }

    public void setBill(Bill bill){
        billdaoimpl.update(bill);
    }

    public Bill queryBillByDate(Date date){

        Bill tempbill=new Bill();
        ArrayList<Bill> temparray =billdaoimpl.findByDate(date);
        Iterator<Bill> it=temparray.iterator();
        while(it.hasNext()){
            tempbill=it.next();
            if(tempbill.getAccountId().equals(accountId)) {
                return tempbill;
            }
        }
        return null;
    }
}
