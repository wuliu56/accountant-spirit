package com.example.as.dao.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.as.dao.BillDBOpenHelper;
import com.example.as.dao.BillDao;
import com.example.as.entity.Bill;
import com.example.as.entity.Wallet;

import java.sql.Date;
import java.util.ArrayList;

public class BillDaoImpl implements BillDao {
    public final BillDBOpenHelper dbOpenHelper;

    public BillDaoImpl() {
        dbOpenHelper=new BillDBOpenHelper(null,null,null,1);
    }

    @Override
    public boolean insert(Bill bill) {
        ContentValues values=new ContentValues();
        values.put("accountId",bill.getAccountId());
        values.put("billId",bill.getId());
        values.put("walletId",bill.getWallet().getId());
        values.put("typeId",bill.getType().getId());
        values.put("amount",bill.getAmount());
        values.put("date",bill.getDate().toString());
        dbOpenHelper.getWritableDatabase().insert("tb_bill",null,values);
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }

    @Override
    public boolean deleteByBillId(String billId) {
        dbOpenHelper.getWritableDatabase().execSQL("delete from tb_bill where billId=?", new Object[]{billId});
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }

    @Override
    public boolean update(Bill bill) {
        dbOpenHelper.getWritableDatabase().execSQL("update tb_bill set amount=? where billId=?", new Object[]{bill.getAmount(),bill.getId()});
        dbOpenHelper.getWritableDatabase().execSQL("update tb_bill set date=? where billId=?", new Object[]{bill.getDate().toString(),bill.getId()});
        dbOpenHelper.getWritableDatabase().execSQL("update tb_bill set typeId=? where billId=?", new Object[]{bill.getType().getId(),bill.getId()});
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }


    @Override
    public ArrayList<Bill> findByAmount(double amount) {
        ArrayList<Bill> list = new ArrayList<>();
        SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from tb_bill where amount=?",new String[]{String.valueOf(amount)});
            if(cursor.moveToNext()==false)return null;
            while (cursor.moveToNext()) {
                String accountid = cursor.getString(cursor.getColumnIndex("accountId"));
                String bid = cursor.getString(cursor.getColumnIndex("billId"));
                String wid = cursor.getString(cursor.getColumnIndex("walletId"));
                String tid=cursor.getString(cursor.getColumnIndex("typeId"));
                double aamount=cursor.getDouble(cursor.getColumnIndex("amount"));
                String ddate=cursor.getString(cursor.getColumnIndex("date"));
                Bill bill = new Bill();
                bill.setAccountId(accountid);
                bill.setId(bid);
                bill.setWallet(new Wallet());
                bill.getType().setId(tid);
                bill.setAmount(aamount);
                bill.setDate(Date.valueOf(ddate));
                list.add(bill);
            }
            cursor.close();
            db.close();
        }
        return list;
    }

    @Override
    public ArrayList<Bill> findByDate(Date date) {
        ArrayList<Bill> list = new ArrayList<>();
        SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from tb_bill where date=?",new String[]{String.valueOf(date)});
            if(cursor.moveToNext()==false)return null;
            while (cursor.moveToNext()) {
                String accountid = cursor.getString(cursor.getColumnIndex("accountId"));
                String bid = cursor.getString(cursor.getColumnIndex("billId"));
                String wid = cursor.getString(cursor.getColumnIndex("walletId"));
                String tid=cursor.getString(cursor.getColumnIndex("typeId"));
                double aamount=cursor.getDouble(cursor.getColumnIndex("amount"));
                String ddate=cursor.getString(cursor.getColumnIndex("date"));
                Bill bill = new Bill();
                bill.setAccountId(accountid);
                bill.setId(bid);
                bill.getWallet().setId(wid);
                bill.getType().setId(tid);
                bill.setAmount(aamount);
                bill.setDate(Date.valueOf(ddate));
                list.add(bill);
            }
            cursor.close();
            db.close();
        }
        return list;
    }

    @Override
    public ArrayList<Bill> findByType(String typeId) {
        ArrayList<Bill> list = new ArrayList<>();
        SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from tb_bill where typeId=?",new String[]{typeId});
            if(cursor.moveToNext()==false)return null;
            while (cursor.moveToNext()) {
                String accountid = cursor.getString(cursor.getColumnIndex("accountId"));
                String bid = cursor.getString(cursor.getColumnIndex("billId"));
                String wid = cursor.getString(cursor.getColumnIndex("walletId"));
                String tid=cursor.getString(cursor.getColumnIndex("typeId"));
                double aamount=cursor.getDouble(cursor.getColumnIndex("amount"));
                String ddate=cursor.getString(cursor.getColumnIndex("date"));
                Bill bill = new Bill();
                bill.setAccountId(accountid);
                bill.setId(bid);
                bill.getWallet().setId(wid);
                bill.getType().setId(tid);
                bill.setAmount(aamount);
                bill.setDate(Date.valueOf(ddate));
                list.add(bill);
            }
            cursor.close();
            db.close();
        }
        return list;
    }

    @Override
    public ArrayList<Bill> findAll(String accountId) {
        ArrayList<Bill> list = new ArrayList<>();
        SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from tb_bill where accountId=?",new String[]{accountId});
            if(cursor.moveToNext()==false)return null;
            while (cursor.moveToNext()) {
                String accountid = cursor.getString(cursor.getColumnIndex("accountId"));
                String bid = cursor.getString(cursor.getColumnIndex("billId"));
                String wid = cursor.getString(cursor.getColumnIndex("walletId"));
                String tid=cursor.getString(cursor.getColumnIndex("typeId"));
                double aamount=cursor.getDouble(cursor.getColumnIndex("amount"));
                String ddate=cursor.getString(cursor.getColumnIndex("date"));
                Bill bill = new Bill();
                bill.setAccountId(accountid);
                bill.setId(bid);
                bill.getWallet().setId(wid);
                bill.getType().setId(tid);
                bill.setAmount(aamount);
                bill.setDate(Date.valueOf(ddate));
                list.add(bill);
            }
            cursor.close();
            db.close();
        }
        return list;
    }
}
