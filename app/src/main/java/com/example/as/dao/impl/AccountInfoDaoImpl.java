package com.example.as.dao.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.as.dao.AccountInfoDBOpenHelper;
import com.example.as.dao.AccountInfoDao;
import com.example.as.entity.AccountInfo;

import java.util.ArrayList;
import java.util.List;

public class AccountInfoDaoImpl implements AccountInfoDao {
    public final AccountInfoDBOpenHelper dbOpenHelper;

    public AccountInfoDaoImpl() {
        dbOpenHelper=new AccountInfoDBOpenHelper(null,null,null,1);
    }
    @Override
    public boolean insert(AccountInfo accountInfo) {
        ContentValues values=new ContentValues();
        values.put("accountId",accountInfo.getAccountID());
        values.put("name",accountInfo.getName());
        values.put("sex",accountInfo.getSex());
        values.put("age",accountInfo.getAge());
        values.put("position",accountInfo.getPosition());
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
    public boolean updateName(AccountInfo accountInfo, String name) {
        dbOpenHelper.getWritableDatabase().execSQL("update tb_accountinfo set name=? where accountId=?", new Object[]{name,accountInfo.getAccountID()});
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }

    @Override
    public boolean updateSex(AccountInfo accountInfo, String sex) {
        dbOpenHelper.getWritableDatabase().execSQL("update tb_accountinfo set sex=? where accountId=?", new Object[]{sex,accountInfo.getAccountID()});
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }

    @Override
    public boolean updateAge(AccountInfo accountInfo, int age) {
        dbOpenHelper.getWritableDatabase().execSQL("update tb_accountinfo set age=? where accountId=?", new Object[]{age,accountInfo.getAccountID()});
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }

    @Override
    public boolean updatePosition(AccountInfo accountInfo, String position) {
        dbOpenHelper.getWritableDatabase().execSQL("update tb_accountinfo set position=? where accountId=?", new Object[]{position,accountInfo.getAccountID()});
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }

    @Override
    public boolean updateIcon(AccountInfo accountInfo, String icon) {
        dbOpenHelper.getWritableDatabase().execSQL("update tb_accountinfo set icon=? where accountId=?", new Object[]{icon,accountInfo.getAccountID()});
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }

    @Override
    public List<AccountInfo> findByAccountId(String accountId) {
        List<AccountInfo> list = new ArrayList<>();
        SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from tb_accountinfo where accountId=?", new String[]{accountId});
            if(cursor.moveToNext()==false)return null;
            while (cursor.moveToNext()) {
                String ppid = cursor.getString(cursor.getColumnIndex("accountId"));
                String nname = cursor.getString(cursor.getColumnIndex("name"));
                String ssex=cursor.getString(cursor.getColumnIndex("sex"));
                int aage=cursor.getInt(cursor.getColumnIndex("age"));
                String pposition=cursor.getString(cursor.getColumnIndex("position"));
                String iicon=cursor.getString(cursor.getColumnIndex("icon"));
                AccountInfo accountInfo = new AccountInfo();
                accountInfo.setAccountID(ppid);
                accountInfo.setName(nname);
                accountInfo.setSex(ssex);
                accountInfo.setAge(aage);
                accountInfo.setPosition(pposition);
                accountInfo.setIcon(iicon);
                list.add(accountInfo);
            }
            cursor.close();
            db.close();
        }
        return list;
        }
}
