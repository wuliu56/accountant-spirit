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
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        values.put("billId",String.valueOf(bill.getId()));
        values.put("walletId",String.valueOf(bill.getWallet().getId()));
        values.put("typeId",String.valueOf(bill.getType().getId()));
        values.put("amount",String.valueOf(bill.getAmount()));
        values.put("date",bill.getDate().toString());
        billDBOpenHelper.getWritableDatabase().insert("tb_bill",null,values);
        billDBOpenHelper.getWritableDatabase().close();
        return true;
    }

    @Override
    public boolean deleteByBillId(Integer billId) {
        billDBOpenHelper.getWritableDatabase().execSQL("delete from tb_bill where billId=?", new String[]{String.valueOf(billId)});
        billDBOpenHelper.getWritableDatabase().close();
        return true;
    }

    @Override
    public boolean update(Bill bill) {
        billDBOpenHelper.getWritableDatabase().execSQL("update tb_bill set amount=? where billId=?", new String[]{String.valueOf(bill.getAmount()), String.valueOf(bill.getId())});
        billDBOpenHelper.getWritableDatabase().execSQL("update tb_bill set date=? where billId=?", new String[]{bill.getDate().toString(), String.valueOf(bill.getId())});
        billDBOpenHelper.getWritableDatabase().execSQL("update tb_bill set typeId=? where billId=?", new String[]{String.valueOf(bill.getType().getId()), String.valueOf(bill.getId())});
        billDBOpenHelper.getWritableDatabase().close();
        return true;
    }


    @Override
    public ArrayList<Bill> findByDate(Date date,String accountId) {
        ArrayList<Bill> list = new ArrayList<Bill>();
        int year_out = date.getYear();
        int month_out = date.getMonth();
        int day_out = date.getDate();//要找的日期
        SQLiteDatabase db= billDBOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from tb_bill where accountId=?",new String[]{accountId});

            while (cursor.moveToNext()) {
                String accountid = cursor.getString(cursor.getColumnIndex("accountId"));
                int bid = cursor.getInt(cursor.getColumnIndex("billId"));
                int wid = cursor.getInt(cursor.getColumnIndex("walletId"));
                int tid=cursor.getInt(cursor.getColumnIndex("typeId"));
                double aamount=cursor.getDouble(cursor.getColumnIndex("amount"));
                String dateString =cursor.getString(cursor.getColumnIndex("date"));
                Date date_in = Date.valueOf(dateString);

                int year_in = date.getYear();
                int month_in = date.getMonth();
                int day_in = date.getDate();//数据库内的日期
                if(year_in == year_out&&month_in == month_out&&day_in == day_out) {
                    Type ttype = typeDaoImpl.findByTypeId(tid, accountid);
                    Wallet wwallet = walletDaoImpl.findByWalletId(wid, accountid);
                    Bill bill = new Bill(bid, aamount, date_in, ttype, wwallet, accountid);
                    list.add(bill);
                }
            }
            cursor.close();
            db.close();
        }
        return list;
    }

    @Override
    public ArrayList<Bill> findByType(int typeId, String accountId) {
        ArrayList<Bill> list = new ArrayList<Bill>();
        SQLiteDatabase db= billDBOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from tb_bill where typeId=? and accountId=?",new String[]{String.valueOf(typeId), accountId});

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
            return list;
        }
        return null;
    }

    @Override
    public ArrayList<Bill> findAll(String accountId) {
        ArrayList<Bill> list = new ArrayList<Bill>();
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
            return list;
        }
       return null;
    }
}
