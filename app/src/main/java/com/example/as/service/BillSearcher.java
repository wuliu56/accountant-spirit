package com.example.as.service;

import com.example.as.dao.impl.BillDaoImpl;
import com.example.as.entity.Bill;
import com.example.as.entity.DailyBill;
import com.example.as.entity.Type;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;


public class BillSearcher {

    String accountId;
    BillDaoImpl billdaoimpl;

    public DailyBill queryDailyBill(Date date){
        DailyBill tempdailybill=new DailyBill();
        Bill tempbill=new Bill();
        ArrayList<Bill> temparray =billdaoimpl.findByDate(date, accountId);
        ArrayList<Bill> target=new ArrayList<Bill>();
        Iterator<Bill> it=temparray.iterator();
        while(it.hasNext()){
            tempbill=it.next();
            if(tempbill.getAccountId().equals(accountId)) {
                target.add(tempbill);
            }
        }
        tempdailybill.setDailyBillArray(target);
        return tempdailybill;
    }


    public ArrayList<Bill> queryBillsByType(Type type){
        Integer typeId=type.getId();
        Bill tempbill=new Bill();
        ArrayList<Bill> temparray =billdaoimpl.findByType(typeId, accountId);
        ArrayList<Bill> target=new ArrayList<Bill>();
        Iterator<Bill> it=temparray.iterator();
        while(it.hasNext()){
            tempbill=it.next();
            if(tempbill.getAccountId().equals(accountId)) {
                target.add(tempbill);
            }
        }
        return target;
    }
}
