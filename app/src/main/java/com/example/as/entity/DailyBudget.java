package com.example.as.entity;

import java.sql.Date;

public class DailyBudget extends Budget {

    //默认构造方法
    public DailyBudget(){
        super();
    }

    //用于向数据库插入时构造
    public DailyBudget(double amount, Date date, double balance, String acccountId){
        super(amount, date, balance, acccountId);
    }

    //用于从数据库查询时构造
    public DailyBudget(Integer id, double amount, Date date, double balance, String acccountId){
        super(id, amount, date, balance, acccountId);
    }

    public int getYear() {
        return getDate().getYear();
    }

    public void setYear(int year) {
        Date date = getDate();
        date.setYear(year);
        setDate(date);
    }

    //实际的月份
    public int getMonth() {
        return getDate().getMonth() + 1;
    }

    //参数是实际的月份
    public void setMonth(int month) {
        Date date = getDate();
        date.setMonth(month - 1);
        setDate(date);
    }

    public int getDay() {
        return getDate().getDay();
    }

    public void setDay(int day) {
        Date date = getDate();
        date.setDate(day);
        setDate(date);
    }
}
