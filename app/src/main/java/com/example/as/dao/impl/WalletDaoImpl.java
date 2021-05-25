package com.example.as.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.as.dao.WalletDBOpenHelper;
import com.example.as.dao.WalletDao;
import com.example.as.entity.Wallet;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class WalletDaoImpl implements WalletDao {
    public final WalletDBOpenHelper dbOpenHelper;

    public WalletDaoImpl(Context context) {
        dbOpenHelper=new WalletDBOpenHelper(context,"wallet.db",null,1);
    }

    @Override
    public boolean insert(Wallet wallet) {
        ContentValues values=new ContentValues();
        values.put("accountId",wallet.getAccountId());
        values.put("walletId",wallet.getId());
        values.put("name",wallet.getName());
        values.put("amount",wallet.getAmount());
        dbOpenHelper.getWritableDatabase().insert("tb_wallet",null,values);
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }

    @Override
    public boolean deleteByName(String name,String accountId) {
        dbOpenHelper.getWritableDatabase().execSQL("delete from tb_wallet where name=? and accountId=?"  , new Object[]{name,accountId});
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }

    @Override
    public boolean updateWallet(String name, Wallet wallet) {

        dbOpenHelper.getWritableDatabase().execSQL("update tb_wallet set amount=? where mame=? and walletId=?", new Object[]{wallet.getAmount(),name,wallet.getId()});
        dbOpenHelper.getWritableDatabase().execSQL("update tb_wallet set name=? where name=? and walletId=?", new Object[]{wallet.getName(),name,wallet.getId()});
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }



    @Override
    public Wallet findByName(String name) {
        Wallet wallet=null;
        SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from tb_wallet where name=?", new String[]{name});

            while (cursor.moveToNext()) {
                String accountid = cursor.getString(cursor.getColumnIndex("accountId"));
                String iid = cursor.getString(cursor.getColumnIndex("walletId"));
                String nname = cursor.getString(cursor.getColumnIndex("name"));
                double aamount=cursor.getDouble(cursor.getColumnIndex("amount"));

                wallet.setAccountId(accountid);
                wallet.setId(iid);
                wallet.setName(nname);
                wallet.setAmount(aamount);

            }
            cursor.close();
            db.close();
        }
        return wallet;
    }

    @Override
    public ArrayList<Wallet> findByAccountId(String id) {
        ArrayList<Wallet> list = new ArrayList<Wallet>();
        SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from tb_wallet where accountId=?", new String[]{id});

            while (cursor.moveToNext()) {
                String accountid = cursor.getString(cursor.getColumnIndex("accountId"));
                String iid = cursor.getString(cursor.getColumnIndex("walletId"));
                String nname = cursor.getString(cursor.getColumnIndex("name"));
                double aamount=cursor.getDouble(cursor.getColumnIndex("amount"));
                Wallet wallet = new Wallet(iid, nname, aamount, accountid);
                list.add(wallet);
            }
            cursor.close();
            db.close();
        }
        return list;
    }

    public int getSize(){
        int count=0;
        SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from tb_wallet",null);

            while (cursor.moveToNext()) {
                count++;
            }
            cursor.close();
            db.close();
        }
        return count;
    }
}
