package com.example.as.service;

import com.example.as.dao.impl.BillDaoImpl;
import com.example.as.entity.Bill;
import com.example.as.entity.DailyBill;
import com.example.as.entity.Type;
import com.example.as.view.account_management.AsApplication;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;


public class BillSearcher {

    String accountId = AsApplication.getAccountId();
    BillDaoImpl billdaoimpl = new BillDaoImpl(AsApplication.getContext());

    public DailyBill queryDailyBill(Date date){//参数date类型为年月日  返回这一天的dailybill

        DailyBill tempdailybill=new DailyBill();
        ArrayList<Bill> temparray =billdaoimpl.findByDate(date,accountId);
        tempdailybill.setDailyBillArray(temparray);
        return tempdailybill;
    }

    public ArrayList<Bill> queryBillsByType(Type type){
        Integer typeId=type.getId();
        ArrayList<Bill> temparray =billdaoimpl.findByType(typeId,accountId);
        return temparray;
    }

    public double queryTypeAmountOfDay(Type type,Date date){//查询某一天 所有某个类的bill的amount之和
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        ArrayList<Bill> list=billdaoimpl.findByDate(date, accountId);//返回这一天所有的bill
        Iterator<Bill>it=list.iterator();
        Bill temp=null;
        double amount=0;
        while(it.hasNext()){
            temp=it.next();
            if(temp.getType().getName().equals(type.getName())){//遍历这一天的所有bill 如果类型相同 则加上这条bill的amount
                amount+=temp.getAmount();
            }
        }
        return amount;
    }

    public double queryTypeAmountOfMonth(Type type,int year,int month){
        ArrayList<Bill>list=billdaoimpl.findByType(type.getId(),accountId);//返回这个类型的所有bill
        Iterator<Bill>it=list.iterator();
        Bill temp=null;
        Date date;
        double amount=0;
        while(it.hasNext()){
            temp=it.next();
            date=temp.getDate();
            Calendar cal=Calendar.getInstance();
            cal.setTime(date);
            if(cal.get(Calendar.YEAR)==year&&cal.get(Calendar.MONTH)+1==month){
                amount+=temp.getAmount();
            }

        }
        return amount;
    }

    public double queryMonthlyOutAmount(int year,int month){
        ArrayList<Bill>list=billdaoimpl.findAll(accountId);//返回这个类型的所有bill
        Iterator<Bill>it=list.iterator();
        Bill temp=null;
        Date date;
        double amount=0;
        while(it.hasNext()){
            temp=it.next();
            date=temp.getDate();
            Calendar cal=Calendar.getInstance();
            cal.setTime(date);
            if(cal.get(Calendar.YEAR)==year&&cal.get(Calendar.MONTH)+1==month){
                amount+=temp.getAmount();
            }
        }
        return amount-queryMonthlyInAmount(year, month);
    }

    public double queryMonthlyInAmount(int year,int month){
        ArrayList<Bill>list=billdaoimpl.findAll(accountId);//返回这个类型的所有bill
        Iterator<Bill>it=list.iterator();
        Bill temp=null;
        Date date;
        double amount=0;
        while(it.hasNext()){
            temp=it.next();
            date=temp.getDate();
            Calendar cal=Calendar.getInstance();
            cal.setTime(date);
            if(cal.get(Calendar.YEAR)==year&&cal.get(Calendar.MONTH)+1==month&&temp.getType().getCategory().equals("收入")){
                amount+=temp.getAmount();
            }
        }
        return amount;
    }


    public double queryTypeAmountOfYear(Type type,int year){
        ArrayList<Bill>list=billdaoimpl.findByType(type.getId(),accountId);//返回这个类型的所有bill
        Iterator<Bill>it=list.iterator();
        Bill temp=null;
        Date date;
        double amount=0;
        while(it.hasNext()){
            temp=it.next();
            date=temp.getDate();
            Calendar cal=Calendar.getInstance();
            cal.setTime(date);
            if(cal.get(Calendar.YEAR)==year){
                amount+=temp.getAmount();
            }
        }
        return amount;
    }

    public double queryYearlyOutAmount(int year){
        ArrayList<Bill>list=billdaoimpl.findAll(accountId);//返回这个类型的所有bill
        Iterator<Bill>it=list.iterator();
        Bill temp=null;
        Date date;
        double amount=0;
        while(it.hasNext()){
            temp=it.next();
            date=temp.getDate();
            Calendar cal=Calendar.getInstance();
            cal.setTime(date);
            if(cal.get(Calendar.YEAR)==year){
                amount+=temp.getAmount();
            }
        }
        return amount-queryYearlyInAmount(year);
    }

    public double queryYearlyInAmount(int year){
        ArrayList<Bill>list=billdaoimpl.findAll(accountId);//返回这个类型的所有bill
        Iterator<Bill>it=list.iterator();
        Bill temp=null;
        Date date;
        double amount=0;
        while(it.hasNext()){
            temp=it.next();
            date=temp.getDate();
            if(date.getYear()+1900==year&&(temp.getType().getCategory()).equals("收入")){
                amount+=temp.getAmount();
            }
        }
        return amount;
    }
}
