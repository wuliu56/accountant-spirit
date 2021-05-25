package com.example.as.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.as.dao.BillDBOpenHelper;
import com.example.as.dao.BillDao;
import com.example.as.dao.TypeDBOpenHelper;
import com.example.as.dao.WalletDBOpenHelper;
import com.example.as.entity.Bill;
import com.example.as.entity.Type;
import com.example.as.entity.Wallet;

import java.sql.Date;
import java.util.ArrayList;

public class BillDaoImpl implements BillDao {
    public final BillDBOpenHelper billDBOpenHelper;
    public final WalletDaoImpl walletDaoImpl;
    public final TypeDaoImpl typeDaoImpl;

    public BillDaoImpl(Context context) {
        billDBOpenHelper = new BillDBOpenHelper(context,"bill.db",null,1);
        walletDaoImpl  = new WalletDaoImpl(context);
        typeDaoImpl = new TypeDaoImpl(context);
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
        billDBOpenHelper.getWritableDatabase().insert("tb_bill",null,values);
        billDBOpenHelper.getWritableDatabase().close();
        return true;
    }

    @Override
    public boolean deleteByBillId(Integer billId) {
        billDBOpenHelper.getWritableDatabase().execSQL("delete from tb_bill where billId=?", new Object[]{billId});
        billDBOpenHelper.getWritableDatabase().close();
        return true;
    }

    @Override
    public boolean update(Bill bill) {
        billDBOpenHelper.getWritableDatabase().execSQL("update tb_bill set amount=? where billId=?", new Object[]{bill.getAmount(),bill.getId()});
        billDBOpenHelper.getWritableDatabase().execSQL("update tb_bill set date=? where billId=?", new Object[]{bill.getDate().toString(),bill.getId()});
        billDBOpenHelper.getWritableDatabase().execSQL("update tb_bill set typeId=? where billId=?", new Object[]{bill.getType().getId(),bill.getId()});
        billDBOpenHelper.getWritableDatabase().close();
        return true;
    }


    @Override
    public ArrayList<Bill> findByDate(Date date) {
        ArrayList<Bill> list = null;
        SQLiteDatabase db= billDBOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from tb_bill where date=?",new String[]{String.valueOf(date)});

            while (cursor.moveToNext()) {
                String accountid = cursor.getString(cursor.getColumnIndex("accountId"));
                int bid = cursor.getInt(cursor.getColumnIndex("billId"));
                int wid = cursor.getInt(cursor.getColumnIndex("walletId"));
                int tid=cursor.getInt(cursor.getColumnIndex("typeId"));
                double aamount=cursor.getDouble(cursor.getColumnIndex("amount"));
                String ddate=cursor.getString(cursor.getColumnIndex("date"));

                Type ttype = typeDaoImpl.findByTypeId(tid, accountid);
                Wallet wwallet = walletDaoImpl.findByWalletId(wid, accountid);
                Bill bill = new Bill(bid, aamount, Date.valueOf(ddate), ttype, wwallet, accountid);
                list.add(bill);
            }
            cursor.close();
            db.close();
        }
        return list;
    }

    @Override
    public ArrayList<Bill> findByType(int typeId) {
        ArrayList<Bill> list = null;
        SQLiteDatabase db= billDBOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from tb_bill where typeId=?",new String[]{String.valueOf(typeId)});

            while (cursor.moveToNext()) {
                String accountid = cursor.getString(cursor.getColumnIndex("accountId"));
                int bid = cursor.getInt(cursor.getColumnIndex("billId"));
                int wid = cursor.getInt(cursor.getColumnIndex("walletId"));
                int tid=cursor.getInt(cursor.getColumnIndex("typeId"));
                double aamount=cursor.getDouble(cursor.getColumnIndex("amount"));
                String ddate=cursor.getString(cursor.getColumnIndex("date"));

                Type ttype = typeDaoImpl.findByTypeId(tid, accountid);
                Wallet wwallet = walletDaoImpl.findByWalletId(wid, accountid);
                Bill bill = new Bill(bid, aamount, Date.valueOf(ddate), ttype, wwallet, accountid);
                list.add(bill);
            }
            cursor.close();
            db.close();
        }
        return list;
    }

    @Override
    public ArrayList<Bill> findAll(String accountId) {
        ArrayList<Bill> list = null;
        SQLiteDatabase db= billDBOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from tb_bill where accountId=?",new String[]{accountId});

            while (cursor.moveToNext()) {
                String accountid = cursor.getString(cursor.getColumnIndex("accountId"));
                int bid = cursor.getInt(cursor.getColumnIndex("billId"));
                int wid = cursor.getInt(cursor.getColumnIndex("walletId"));
                int tid=cursor.getInt(cursor.getColumnIndex("typeId"));
                double aamount=cursor.getDouble(cursor.getColumnIndex("amount"));
                String ddate=cursor.getString(cursor.getColumnIndex("date"));

                Type ttype = typeDaoImpl.findByTypeId(tid, accountid);
                Wallet wwallet = walletDaoImpl.findByWalletId(wid, accountid);
                Bill bill = new Bill(bid, aamount, Date.valueOf(ddate), ttype, wwallet, accountid);
                list.add(bill);
            }
            cursor.close();
            db.close();
        }
        return list;
    }
}
