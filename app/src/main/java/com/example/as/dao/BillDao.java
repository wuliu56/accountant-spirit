package com.example.as.dao;

import com.example.as.entity.Bill;
import com.example.as.entity.Type;

import java.sql.Date;
import java.util.ArrayList;

public interface BillDao {
    public boolean insert(Bill bill);
    public boolean deleteByBillId(Integer billId);
    public boolean update(Bill bill);
    public ArrayList<Bill> findByDate(Date date);
    public ArrayList<Bill> findByType(int typeId);
    public ArrayList<Bill> findAll(String accountId);
}
