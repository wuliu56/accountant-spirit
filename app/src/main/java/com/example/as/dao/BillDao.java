package com.example.as.dao;

import com.example.as.entity.Bill;
import com.example.as.entity.Type;

import java.sql.Date;
import java.util.ArrayList;

public interface BillDao {
    public boolean insert(Bill bill);
    public boolean deleteByBillId(String billId);
    public boolean update(Bill bill);
    public ArrayList<Bill> findByAmount(double amount);
    public ArrayList<Bill> findByDate(Date date);
    public ArrayList<Bill> findByType(String typeId);
    public ArrayList<Bill> findAll(String accountId);
}
