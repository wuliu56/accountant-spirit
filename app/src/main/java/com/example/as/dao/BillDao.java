package com.example.as.dao;

import com.example.as.entity.Bill;

import java.sql.Date;
import java.util.List;

public interface BillDao {
    public boolean insert(Bill bill);
    public boolean deleteByBillId(String billId);
    public List<Bill> findByAmount(double amount);
    public List<Bill> findByDate(Date date);
    public List<Bill> findAll(String accountId);
}
