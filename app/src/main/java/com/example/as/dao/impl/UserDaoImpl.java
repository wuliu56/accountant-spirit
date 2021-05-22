package com.example.as.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.as.dao.UserDBOpenHelper;
import com.example.as.dao.UserDao;
import com.example.as.entity.Account;
import com.example.as.entity.CurrencyList;

import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    public final UserDBOpenHelper dbOpenHelper;

    public UserDaoImpl(Context context) {
        dbOpenHelper=new UserDBOpenHelper(context,"user.db",null,1);
    }

    @Override
    public boolean insert(Account user) {
        ContentValues values=new ContentValues();
        values.put("accountId",user.getAccountId());
        values.put("username",user.getUsername());
        values.put("pwd",user.getPassword());
        values.put("currency",user.getCurrency().getName());
        dbOpenHelper.getWritableDatabase().insert("tb_user",null,values);
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }

    @Override
    public boolean deleteByAccountId(String id) {
        dbOpenHelper.getWritableDatabase().execSQL("delete from tb_user where accountId=?", new Object[]{id});
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }

    @Override
    public boolean updatePwd(String username,String password) {
        dbOpenHelper.getWritableDatabase().execSQL("update tb_user set pwd=? where username=?", new Object[]{password,username});
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }

    @Override
    public boolean updateUsername(String accountId,String username) {
        dbOpenHelper.getWritableDatabase().execSQL("update tb_user set username=? where accountId=?", new Object[]{username,accountId});
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }

    @Override
    public boolean updateCurrency(String accountId, String currency) {
        dbOpenHelper.getWritableDatabase().execSQL("update tb_user set currency=? where accountId=?", new Object[]{currency,accountId});
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }


    @Override
    public Account findByUsername(String username) {

        Account account = new Account();
        SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from tb_user where username=?", new String[]{username});
            System.out.println("进入db");
            //if(cursor.moveToNext()==false) return null;
            while (cursor.moveToNext()) {
                String accountid = cursor.getString(cursor.getColumnIndex("accountId"));
                String name = cursor.getString(cursor.getColumnIndex("username"));
                String ppwd=cursor.getString(cursor.getColumnIndex("pwd"));
                String currency=cursor.getString(cursor.getColumnIndex("currency"));
                System.out.println("进入while");
                System.out.println(accountid);
                System.out.println(name);
                System.out.println(ppwd);
                System.out.println(currency);

                account.setAccountId(accountid);
                account.setUsername(name);
                account.setPassword(ppwd);
                account.setCurrency(new CurrencyList().getCurrencyByName(currency));
            }
            cursor.close();
            db.close();
        }
        return account;
    }

    public Account findByAccountId(String accountId) {


        SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from tb_user where accountId=?", new String[]{accountId});
            if(cursor.moveToNext()==false)return null;
            while (cursor.moveToNext()) {
                String accountid = cursor.getString(cursor.getColumnIndex("accountId"));
                String name = cursor.getString(cursor.getColumnIndex("username"));
                String ppwd=cursor.getString(cursor.getColumnIndex("pwd"));
                String currency=cursor.getString(cursor.getColumnIndex("currency"));
                Account account = new Account();
                account.setAccountId(accountid);
                account.setUsername(name);
                account.setPassword(ppwd);
                account.getCurrency().setName(currency);
                return account;
            }
            cursor.close();
            db.close();
        }
        return null;
    }

    public int getSize(){
        int count=0;
        SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from tb_user",null);
            if(cursor.moveToNext()==false)return 0;
            while (cursor.moveToNext()) {
               count++;
            }
            cursor.close();
            db.close();
        }
        return count;
    }
}