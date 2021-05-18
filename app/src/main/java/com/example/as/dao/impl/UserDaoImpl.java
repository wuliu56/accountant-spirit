package com.example.as.dao.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.as.dao.UserDBOpenHelper;
import com.example.as.dao.UserDao;
import com.example.as.entity.Account;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

public class UserDaoImpl implements UserDao {

    public final UserDBOpenHelper dbOpenHelper;

    public UserDaoImpl() {
        dbOpenHelper=new UserDBOpenHelper(null,null,null,1);
    }

    @Override
    public boolean insert(Account user) {
        ContentValues values=new ContentValues();
        values.put("accountId",user.getId());
        values.put("username",user.getPassword());
        values.put("pwd",user.getAccountID());
        dbOpenHelper.getWritableDatabase().insert("tb_user",null,values);
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }

    @Override
    public boolean deleteById(String id) {
        dbOpenHelper.getWritableDatabase().execSQL("delete from tb_user where accountId=?", new Object[]{id});
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }

    @Override
    public boolean updatePwd(Account user,String password) {
        dbOpenHelper.getWritableDatabase().execSQL("update tb_user set pwd=? where accountId=?", new Object[]{password,user.getAccountID()});
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }

    @Override
    public boolean updateUsername(Account user,String username) {
        dbOpenHelper.getWritableDatabase().execSQL("update tb_user set username=? where accountId=?", new Object[]{username,user.getAccountID()});
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }


    @Override
    public List<Account> findByAccountId(String id) {
        List<Account> list = new ArrayList<>();
        SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from tb_user where accountId=?", new String[]{id});
            if(cursor.moveToNext()==false)return null;
            while (cursor.moveToNext()) {
                String accountid = cursor.getString(cursor.getColumnIndex("accountId"));
                String name = cursor.getString(cursor.getColumnIndex("username"));
                String ppwd=cursor.getString(cursor.getColumnIndex("pwd"));
                Account account = new Account();
                account.setAccountID(accountid);
                account.setId(name);
                account.setPassword(ppwd);
                list.add(account);
            }
            cursor.close();
            db.close();
        }
        return list;
    }
}