package com.example.as.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.as.dao.AccountInfoDBOpenHelper;
import com.example.as.dao.AccountInfoDao;
import com.example.as.entity.AccountInfo;

import java.sql.Date;
import java.util.ArrayList;

public class AccountInfoDaoImpl implements AccountInfoDao {
    public final AccountInfoDBOpenHelper dbOpenHelper;

    public AccountInfoDaoImpl(Context context) {
        dbOpenHelper=new AccountInfoDBOpenHelper(context,"accountInfo.db",null,1);
    }
    @Override
    public boolean insert(AccountInfo accountInfo) {
        ContentValues values=new ContentValues();
        values.put("accountId",accountInfo.getAccountId());
        values.put("name",accountInfo.getName());
        values.put("sex",accountInfo.getSex());
        values.put("birthday",accountInfo.getBirthday().toString());
        values.put("icon",accountInfo.getIcon());
        dbOpenHelper.getWritableDatabase().insert("tb_accountinfo",null,values);
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }

    @Override
    public boolean deleteByAccountId(String id) {
        dbOpenHelper.getWritableDatabase().execSQL("delete from tb_accountinfo where accountId=?", new Object[]{id});
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }

    @Override
    public boolean updateAccountInfo(AccountInfo accountinfo) {
        dbOpenHelper.getWritableDatabase().execSQL("update tb_accountinfo set name=? where accountId=?", new Object[]{accountinfo.getName(),accountinfo.getAccountId()});
        dbOpenHelper.getWritableDatabase().execSQL("update tb_accountinfo set sex=? where accountId=?", new Object[]{accountinfo.getSex(),accountinfo.getAccountId()});
        dbOpenHelper.getWritableDatabase().execSQL("update tb_accountinfo set birthday=? where accountId=?", new Object[]{accountinfo.getBirthday(),accountinfo.getAccountId()});
        dbOpenHelper.getWritableDatabase().execSQL("update tb_accountinfo set icon=? where accountId=?", new Object[]{accountinfo.getIcon(),accountinfo.getAccountId()});
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }



    @Override
    public AccountInfo findByAccountId(String accountId) {
        AccountInfo accountInfo = null;
        SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from tb_accountinfo where accountId=?", new String[]{accountId});

            while (cursor.moveToNext()) {
                String ppid = cursor.getString(cursor.getColumnIndex("accountId"));
                String nname = cursor.getString(cursor.getColumnIndex("name"));
                String ssex=cursor.getString(cursor.getColumnIndex("sex"));
                String bbirthday=cursor.getString(cursor.getColumnIndex("birthday"));
                String iicon=cursor.getString(cursor.getColumnIndex("icon"));

                accountInfo = new AccountInfo(nname, ssex, Date.valueOf(bbirthday), iicon, ppid);
            }
            cursor.close();
            db.close();
        }
        return accountInfo;
    }
}
