package com.example.as.dao.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.as.dao.BudgetDBOpenHelper;
import com.example.as.dao.BudgetDao;
import com.example.as.entity.Budget;
import com.example.as.entity.Wallet;

import java.util.ArrayList;
import java.util.List;

public class BudgetDaoImpl implements BudgetDao {
    public final BudgetDBOpenHelper dbOpenHelper;

    public BudgetDaoImpl() {
        dbOpenHelper=new BudgetDBOpenHelper(null,null,null,1);
    }

    @Override
    public boolean insert(Budget budget) {
        ContentValues values=new ContentValues();
        values.put("accountId",budget.getAccount().getAccountID());
        values.put("budgetId",budget.getId());
        values.put("amount",budget.getAmount());
        values.put("balance",budget.getBalance());
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
    public boolean updateAmount(Budget budget, double amount) {
        dbOpenHelper.getWritableDatabase().execSQL("update tb_budget set amount=? where budgetId=?", new Object[]{amount,budget.getId()});
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }

    @Override
    public boolean updateBalance(Budget budget, double balance) {
        dbOpenHelper.getWritableDatabase().execSQL("update tb_budget set balance=? where budgetId=?", new Object[]{balance,budget.getId()});
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }

    @Override
    public List<Budget> findByaccountId(String id) {
        List<Budget> list = new ArrayList<>();
        SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from tb_budget where accountId=?", new String[]{id});
            if(cursor.moveToNext()==false)return null;
            while (cursor.moveToNext()) {
                String accountid = cursor.getString(cursor.getColumnIndex("accountId"));
                String buid = cursor.getString(cursor.getColumnIndex("budgetId"));
                double aamount=cursor.getDouble(cursor.getColumnIndex("amount"));
                double bbalance=cursor.getDouble(cursor.getColumnIndex("balance"));
                Budget budget=new Budget();
                budget.setAccountID(accountid);
                budget.setId(buid);
                budget.setAmount(aamount);
                budget.setBalance(bbalance);
                list.add(budget);
            }
            cursor.close();
            db.close();
        }
        return list;
    }

    @Override
    public List<Budget> findBybudgetId(String budgetId) {
        List<Budget> list = new ArrayList<>();
        SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from tb_budget where budgetId=?", new String[]{budgetId});
            if(cursor.moveToNext()==false)return null;
            while (cursor.moveToNext()) {
                String accountid = cursor.getString(cursor.getColumnIndex("accountId"));
                String buid = cursor.getString(cursor.getColumnIndex("budgetId"));
                double aamount=cursor.getDouble(cursor.getColumnIndex("amount"));
                double bbalance=cursor.getDouble(cursor.getColumnIndex("balance"));
                Budget budget=new Budget();
                budget.setAccountID(accountid);
                budget.setId(buid);
                budget.setAmount(aamount);
                budget.setBalance(bbalance);
                list.add(budget);
            }
            cursor.close();
            db.close();
        }
        return list;
    }
}
