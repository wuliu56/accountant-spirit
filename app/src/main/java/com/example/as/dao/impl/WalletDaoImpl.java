package com.example.as.dao.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.as.dao.WalletDBOpenHelper;
import com.example.as.dao.WalletDao;
import com.example.as.entity.AccountInfo;
import com.example.as.entity.Wallet;

import java.util.ArrayList;
import java.util.List;

public class WalletDaoImpl implements WalletDao {
    public final WalletDBOpenHelper dbOpenHelper;

    public WalletDaoImpl() {
        dbOpenHelper=new WalletDBOpenHelper(null,null,null,1);
    }

    @Override
    public boolean insert(Wallet wallet) {
        ContentValues values=new ContentValues();
        values.put("accountId",wallet.getAccount().getAccountID());
        values.put("walletId",wallet.getId());
        values.put("name",wallet.getName());
        values.put("amount",wallet.getAmount());
        dbOpenHelper.getWritableDatabase().insert("tb_wallet",null,values);
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }

    @Override
    public boolean deleteByWalletId(String id) {
        dbOpenHelper.getWritableDatabase().execSQL("delete from tb_wallet where walletId=?", new Object[]{id});
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }

    @Override
    public boolean updateName(Wallet wallet, String name) {
        dbOpenHelper.getWritableDatabase().execSQL("update tb_wallet set name=? where walletId=?", new Object[]{name,wallet.getId()});
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }

    @Override
    public boolean updateAmount(Wallet wallet, double amount) {
        dbOpenHelper.getWritableDatabase().execSQL("update tb_wallet set amount=? where walletId=?", new Object[]{amount,wallet.getId()});
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }

    @Override
    public List<Wallet> findByWalletId(String id) {
        List<Wallet> list = new ArrayList<>();
        SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from tb_wallet where walletId=?", new String[]{id});
            if(cursor.moveToNext()==false)return null;
            while (cursor.moveToNext()) {
                String accountid = cursor.getString(cursor.getColumnIndex("accountId"));
                String iid = cursor.getString(cursor.getColumnIndex("walletId"));
                String nname = cursor.getString(cursor.getColumnIndex("name"));
                double aamount=cursor.getDouble(cursor.getColumnIndex("amount"));
                Wallet wallet=new Wallet();
                wallet.setAccountID(accountid);
                wallet.setId(iid);
                wallet.setName(nname);
                wallet.setAmount(aamount);
                list.add(wallet);
            }
            cursor.close();
            db.close();
        }
        return list;
    }

    @Override
    public List<Wallet> findByAccountId(String id) {
        List<Wallet> list = new ArrayList<>();
        SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from tb_wallet where accountId=?", new String[]{id});
            if(cursor.moveToNext()==false)return null;
            while (cursor.moveToNext()) {
                String accountid = cursor.getString(cursor.getColumnIndex("accountId"));
                String iid = cursor.getString(cursor.getColumnIndex("walletId"));
                String nname = cursor.getString(cursor.getColumnIndex("name"));
                double aamount=cursor.getDouble(cursor.getColumnIndex("amount"));
                Wallet wallet=new Wallet();
                wallet.setAccountID(accountid);
                wallet.setId(iid);
                wallet.setName(nname);
                wallet.setAmount(aamount);
                list.add(wallet);
            }
            cursor.close();
            db.close();
        }
        return list;
    }
}
