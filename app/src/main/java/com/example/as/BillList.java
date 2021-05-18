package com.example.as;

import com.example.as.entity.Bill;
import com.example.as.entity.DailyBill;
import com.example.as.entity.Type;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

public class BillList {

    private ArrayList<DailyBill> dailyBillSet=new ArrayList<DailyBill>();
    private DailyBill temp=new DailyBill();
    private ArrayList<Bill> targets=new ArrayList<Bill>();

    public void newDailyBill(Date date){
        DailyBill dailybill=new DailyBill();
        dailybill.setDate(date);
        dailyBillSet.add(new DailyBill());
    }

    public void deleteDailyBill(Date date){
        Iterator<DailyBill> it=dailyBillSet.iterator();
        while(it.hasNext()){
            temp=it.next();
            if(temp.getDate().equals(date));
            dailyBillSet.remove(temp);
            break;
        }
    }

    public DailyBill queryDailyBill(Date date){
        Iterator<DailyBill> it=dailyBillSet.iterator();
        while(it.hasNext()){
            temp=it.next();
            if(temp.getDate().equals(date));
            return temp;
        }
        return null;
    }

    public ArrayList<Bill> queryBillsByType(Type type){
        Iterator<DailyBill> it=dailyBillSet.iterator();
        while(it.hasNext()){
            temp=it.next();
            targets.addAll(temp.queryBillsByType(type));
        }
        return targets;
    }

    public double getTypeAmount(Type type){
        int typeamount=0;
        Iterator<DailyBill> it=dailyBillSet.iterator();
        while(it.hasNext()){
            temp=it.next();
            typeamount+=temp.getAmountByType(type);
        }
        return typeamount;
    }

    public double getMonthlyAmount(int year,int month) {
        int monthlyamount=0;
        Iterator<DailyBill> it = dailyBillSet.iterator();
        while (it.hasNext()) {
            temp = it.next();
            Calendar cal = Calendar.getInstance();
            cal.setTime(temp.getDate());
            if (cal.get(Calendar.YEAR) == year&&cal.get(Calendar.MONTH) + 1 == month) {
                //月份是从零开始的 当前7月get返回6
                monthlyamount += temp.getDailyAmount();
            }
        }
        return monthlyamount;
    }

    public double getYearlyAmount(int year){
        int yearlyamount=0;
        Iterator<DailyBill> it = dailyBillSet.iterator();
        while (it.hasNext()) {
            temp = it.next();
            Calendar cal = Calendar.getInstance();
            cal.setTime(temp.getDate());
            if (cal.get(Calendar.YEAR)  == year) {
                yearlyamount += temp.getDailyAmount();
            }
        }
        return yearlyamount;
    }

}

