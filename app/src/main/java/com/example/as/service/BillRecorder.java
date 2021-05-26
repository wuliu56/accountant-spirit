package com.example.as.service;

import com.example.as.dao.impl.BillDaoImpl;
import com.example.as.dao.impl.DailyBudgetDaoImpl;
import com.example.as.dao.impl.WalletDaoImpl;
import com.example.as.entity.Bill;
import com.example.as.entity.DailyBill;
import com.example.as.entity.DailyBudget;
import com.example.as.entity.MonthlyBudget;
import com.example.as.entity.Wallet;
import com.example.as.view.account_management.AsApplication;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

public class BillRecorder {

    private String accountId = AsApplication.getAccountId();
    BillDaoImpl billdaoimpl = new BillDaoImpl(AsApplication.getContext());
    DailyBudgetDaoImpl dailybudgetdaoimpl = new DailyBudgetDaoImpl(AsApplication.getContext());
    WalletDaoImpl walletDaoImpl = new WalletDaoImpl(AsApplication.getContext());

    public BillRecorder(){}

    public void newDailyBudget(DailyBudget dailybudget){
        dailybudgetdaoimpl.insert(dailybudget);
    }

    public void deleteDailyBudget(Date date){
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        dailybudgetdaoimpl.deleteByDate(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)+1,cal.get(Calendar.DAY_OF_MONTH),accountId);
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

    public void setDailyBudget(DailyBudget dailyBudget){
        dailybudgetdaoimpl.updateAmount(dailyBudget.getId(),dailyBudget.getAmount());
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
        System.out.println(year+" "+month+" "+day+" "+accountId);
        return dailybudgetdaoimpl.findByDay(60.0, year,month,day,accountId);
    }

    public MonthlyBudget queryMonthlyBudget(int year, int month){
        ArrayList<DailyBudget> dailybudgetarray=dailybudgetdaoimpl.findByMonth(year,month,accountId);
        MonthlyBudget monthlybudget=new MonthlyBudget(dailybudgetarray);
        return monthlybudget;
    }


    public void newBill(Bill bill){
        billdaoimpl.insert(bill);
        updateDailyBudget(bill.getDate());
        updateWallet(bill);
    }

    public void deleteBill(Integer id){
        billdaoimpl.deleteByBillId(id);
    }

    public void setBill(Bill bill){
        billdaoimpl.update(bill);
        updateDailyBudget(bill.getDate());
        updateWallet(bill);
    }

    public DailyBill queryDailyBill(Date date){
        DailyBill dailyBill = new DailyBill();
        dailyBill.setDailyBillArray(billdaoimpl.findByDate(date, accountId));
        return dailyBill;
    }

    public void updateDailyBudget(Date date){//年月日 每次进行增删改bill后更新当前天的budget
        ArrayList<Bill> billarray=new ArrayList<Bill>();
        billarray=billdaoimpl.findByDate(date, accountId);//得到这一天此时所有bill数组
        Iterator<Bill> it=billarray.iterator();
        Bill tempbill=null;
        double spending=0;
        while(it.hasNext()){
            tempbill=it.next();
            if(!tempbill.getType().getCategory().equals("收入")){
                spending+=tempbill.getAmount();//这一天的总支出
            }
        }
        DailyBudget currentdailybudget=queryDailyBudget(date.getYear(), date.getMonth()+1, date.getDate());
        double amount=currentdailybudget.getAmount();
        dailybudgetdaoimpl.updateBalance(currentdailybudget.getId(),amount-spending);
    }

    public void updateWallet(Bill bill){
        Wallet temp=bill.getWallet();
        double amount=bill.getAmount();
        if(bill.getType().getCategory().equals("收入")){
            temp.setAmount(temp.getAmount()+amount);
        }else{
            temp.setAmount(temp.getAmount()-amount);
        }
        walletDaoImpl.updateWallet(temp.getName(),temp);
    }
}
