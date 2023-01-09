package com.example.as.dao;

import com.example.as.entity.Bill;
import com.example.as.entity.Type;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;

public interface BillDao {
    public boolean insert(Bill bill);
    public boolean deleteByBillId(Integer billId);
    public boolean update(Bill bill);
    public Bill findById(Integer id);
    public ArrayList<Bill> findByDate(Date date, String accountId) throws ParseException;
    public ArrayList<Bill> findByType(Integer typeId, String accountId);
    public ArrayList<Bill> findAll(String accountId);
}
