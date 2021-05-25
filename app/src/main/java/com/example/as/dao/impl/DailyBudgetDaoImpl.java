package com.example.as.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.as.dao.DailyBudgetDBOpenHelper;
import com.example.as.dao.DailyBudgetDao;
import com.example.as.entity.Budget;
import com.example.as.entity.DailyBudget;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

public class DailyBudgetDaoImpl implements DailyBudgetDao {
    public final DailyBudgetDBOpenHelper dbOpenHelper;

    public DailyBudgetDaoImpl(Context context) {
        dbOpenHelper=new DailyBudgetDBOpenHelper(context,"dailyBudget.db",null,1);
    }

    @Override
    public boolean insert(DailyBudget budget) {
        ContentValues values=new ContentValues();
        values.put("accountId",budget.getAccountId());
        values.put("budgetId",budget.getId());
        values.put("amount",budget.getAmount());
        values.put("balance",budget.getBalance());
        values.put("year",budget.getYear());
        values.put("month",budget.getMonth());
        values.put("day",budget.getDay());
        dbOpenHelper.getWritableDatabase().insert("tb_budget",null,values);
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }

    @Override
    public boolean deleteByDate(int year,int month,int day,String accountId) {
        dbOpenHelper.getWritableDatabase().execSQL("delete from tb_budget where year=? and month=? and day=? and accountId=?", new Object[]{year,month,day,accountId});
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }

    @Override
    public boolean updateAmount(int budgetId, double amount) {
        dbOpenHelper.getWritableDatabase().execSQL("update tb_budget set amount=? where budgetId=?", new Object[]{amount,budgetId});
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }

    @Override
    public boolean updateBalance(int budgetId, double balance) {
        dbOpenHelper.getWritableDatabase().execSQL("update tb_budget set balance=? where budgetId=?", new Object[]{balance,budgetId});
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }

    @Override
    public boolean updateDay(int budgetId,int year,int month,int day) {
        dbOpenHelper.getWritableDatabase().execSQL("update tb_budget set year=?  where budgetId=?", new Object[]{year,budgetId});
        dbOpenHelper.getWritableDatabase().execSQL("update tb_budget set month=?  where budgetId=?", new Object[]{month,budgetId});
        dbOpenHelper.getWritableDatabase().execSQL("update tb_budget set day=?  where budgetId=?", new Object[]{day,budgetId});
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }

    @Override
    public DailyBudget findByBudgetId(Integer budgetId, String accountId) {
        DailyBudget budget=null;
        SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from tb_budget where budgetId=? and accountId=?",new String[]{String.valueOf(budgetId),accountId});

            while (cursor.moveToNext()) {
                String accountid = cursor.getString(cursor.getColumnIndex("accountId"));
                int buid = cursor.getInt(cursor.getColumnIndex("budgetId"));
                double aamount=cursor.getDouble(cursor.getColumnIndex("amount"));
                double bbalance=cursor.getDouble(cursor.getColumnIndex("balance"));
                int yyear=cursor.getInt(cursor.getColumnIndex("year"));
                int mmonth=cursor.getInt(cursor.getColumnIndex("month"));
                int dday=cursor.getInt(cursor.getColumnIndex("day"));

                Date date = new Date(yyear, mmonth-1, dday);
                budget = new DailyBudget(buid, aamount, date, bbalance, accountid);
            }
            cursor.close();
            db.close();
        }
        return budget;
    }

    @Override
    public ArrayList<DailyBudget> findByAccountId(String id) {
        ArrayList<DailyBudget> list = null;
        SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from tb_budget where accountId=?", new String[]{id});

            while (cursor.moveToNext()) {
                String accountid = cursor.getString(cursor.getColumnIndex("accountId"));
                int buid = cursor.getInt(cursor.getColumnIndex("budgetId"));
                double aamount=cursor.getDouble(cursor.getColumnIndex("amount"));
                double bbalance=cursor.getDouble(cursor.getColumnIndex("balance"));
                int yyear=cursor.getInt(cursor.getColumnIndex("year"));
                int mmonth=cursor.getInt(cursor.getColumnIndex("month"));
                int dday=cursor.getInt(cursor.getColumnIndex("day"));

                DailyBudget budget=new DailyBudget();
                Date date = new Date(yyear, mmonth-1, dday);
                budget = new DailyBudget(buid, aamount, date, bbalance,accountid);
                list.add(budget);
            }
            cursor.close();
            db.close();
        }
        return list;
    }

    @Override
    public DailyBudget findByDay(int year,int month,int day,String accountId) {
        DailyBudget budget=null;
        SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from tb_budget where year=? and month=? and day=? and accountId=?",new String[]{String.valueOf(year),String.valueOf(month),String.valueOf(day),accountId});

            while (cursor.moveToNext()) {
                String accountid = cursor.getString(cursor.getColumnIndex("accountId"));
                int buid = cursor.getInt(cursor.getColumnIndex("budgetId"));
                double aamount=cursor.getDouble(cursor.getColumnIndex("amount"));
                double bbalance=cursor.getDouble(cursor.getColumnIndex("balance"));
                int yyear=cursor.getInt(cursor.getColumnIndex("year"));
                int mmonth=cursor.getInt(cursor.getColumnIndex("month"));
                int dday=cursor.getInt(cursor.getColumnIndex("day"));

                Date date = new Date(yyear, mmonth-1, dday);
                budget = new DailyBudget(buid, aamount, date, bbalance, accountid);
            }
            cursor.close();
            db.close();
        }
        return budget;
    }

    @Override
    public ArrayList<DailyBudget> findByMonth(int year,int month,String accountId) {
        DailyBudget budget=null;
        ArrayList<DailyBudget> dailybudgetarray=new ArrayList<DailyBudget>();
        SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from tb_budget where year=? and month=? and accountId=?",new String[]{String.valueOf(year),String.valueOf(month),accountId});

            while (cursor.moveToNext()) {
                String accountid = cursor.getString(cursor.getColumnIndex("accountId"));
                int buid = cursor.getInt(cursor.getColumnIndex("budgetId"));
                double aamount=cursor.getDouble(cursor.getColumnIndex("amount"));
                double bbalance=cursor.getDouble(cursor.getColumnIndex("balance"));
                int yyear=cursor.getInt(cursor.getColumnIndex("year"));
                int mmonth=cursor.getInt(cursor.getColumnIndex("month"));
                int dday=cursor.getInt(cursor.getColumnIndex("day"));

                Date date = new Date(yyear, mmonth-1, dday);
                budget = new DailyBudget(buid, aamount, date, bbalance, accountid);
                dailybudgetarray.add(budget);
            }
            cursor.close();
            db.close();
        }
        return dailybudgetarray;
    }
}
