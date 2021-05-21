package com.example.as.dao.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.as.dao.DailyBudgetDBOpenHelper;
import com.example.as.dao.DailyBudgetDao;
import com.example.as.entity.DailyBudget;

import java.sql.Date;
import java.util.ArrayList;

public class DailyBudgetDaoImpl implements DailyBudgetDao {
    public final DailyBudgetDBOpenHelper dbOpenHelper;

    public DailyBudgetDaoImpl() {
        dbOpenHelper=new DailyBudgetDBOpenHelper(null,null,null,1);
    }

    @Override
    public boolean insert(DailyBudget budget) {
        ContentValues values=new ContentValues();
        values.put("accountId",budget.getAccountId());
        values.put("budgetId",budget.getId());
        values.put("amount",budget.getAmount());
        values.put("balance",budget.getBalance());
        values.put("date",budget.getDate().toString());
        dbOpenHelper.getWritableDatabase().insert("tb_budget",null,values);
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }

    @Override
    public boolean deleteByBudgetId(String budgetId) {
        dbOpenHelper.getWritableDatabase().execSQL("delete from tb_budget where budgetId=?", new Object[]{budgetId});
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }

    @Override
    public boolean updateAmount(String budgetId, double amount) {
        dbOpenHelper.getWritableDatabase().execSQL("update tb_budget set amount=? where budgetId=?", new Object[]{amount,budgetId});
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }

    @Override
    public boolean updateBalance(String budgetId, double balance) {
        dbOpenHelper.getWritableDatabase().execSQL("update tb_budget set balance=? where budgetId=?", new Object[]{balance,budgetId});
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }

    @Override
    public boolean updateDate(String budgetId, Date date) {
        dbOpenHelper.getWritableDatabase().execSQL("update tb_budget set date=? where budgetId=?", new Object[]{date.toString(),budgetId});
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }

    @Override
    public ArrayList<DailyBudget> findByAccountId(String id) {
        ArrayList<DailyBudget> list = new ArrayList<>();
        SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from tb_budget where accountId=?", new String[]{id});
            if(cursor.moveToNext()==false)return null;
            while (cursor.moveToNext()) {
                String accountid = cursor.getString(cursor.getColumnIndex("accountId"));
                String buid = cursor.getString(cursor.getColumnIndex("budgetId"));
                double aamount=cursor.getDouble(cursor.getColumnIndex("amount"));
                double bbalance=cursor.getDouble(cursor.getColumnIndex("balance"));
                String date=cursor.getString(cursor.getColumnIndex("date"));
                DailyBudget budget=new DailyBudget();
                budget.setAccountId(accountid);
                budget.setId(buid);
                budget.setAmount(aamount);
                budget.setBalance(bbalance);
                budget.setDate(Date.valueOf(date));
                list.add(budget);
            }
            cursor.close();
            db.close();
        }
        return list;
    }

    @Override
    public DailyBudget findByBudgetId(String budgetId) {
        DailyBudget budget=new DailyBudget();
        SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from tb_budget where budgetId=?", new String[]{budgetId});
            if(cursor.moveToNext()==false)return null;
            while (cursor.moveToNext()) {
                String accountid = cursor.getString(cursor.getColumnIndex("accountId"));
                String buid = cursor.getString(cursor.getColumnIndex("budgetId"));
                double aamount=cursor.getDouble(cursor.getColumnIndex("amount"));
                double bbalance=cursor.getDouble(cursor.getColumnIndex("balance"));
                String date=cursor.getString(cursor.getColumnIndex("date"));
                budget.setAccountId(accountid);
                budget.setId(buid);
                budget.setAmount(aamount);
                budget.setBalance(bbalance);
                budget.setDate(Date.valueOf(date));
            }
            cursor.close();
            db.close();
        }
        return budget;
    }
}
