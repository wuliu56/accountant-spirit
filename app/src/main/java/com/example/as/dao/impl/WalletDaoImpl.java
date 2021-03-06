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
        values.put("amount",String.valueOf(wallet.getAmount()));
        dbOpenHelper.getWritableDatabase().insert("tb_wallet",null,values);
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }

    @Override
    public boolean deleteByName(String name,String accountId) {
        dbOpenHelper.getWritableDatabase().execSQL("delete from tb_wallet where name=? and accountId=?"  , new String[]{name,accountId});
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }

    @Override
    public boolean updateWallet(String name, Wallet wallet) {

        dbOpenHelper.getWritableDatabase().execSQL("update tb_wallet set amount=? where name=? and walletId=?", new String[]{String.valueOf(wallet.getAmount()),name, String.valueOf(wallet.getId())});
        dbOpenHelper.getWritableDatabase().execSQL("update tb_wallet set name=? where name=? and walletId=?", new String[]{wallet.getName(),name, String.valueOf(wallet.getId())});
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }



    @Override
    public Wallet findByName(String name, String accountId) {
        Wallet wallet=null;
        SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from tb_wallet where name=? and accountId=?", new String[]{name,accountId});

            while (cursor.moveToNext()) {
                String accountid = cursor.getString(cursor.getColumnIndex("accountId"));
                int iid = cursor.getInt(cursor.getColumnIndex("walletId"));
                String nname = cursor.getString(cursor.getColumnIndex("name"));
                double aamount=cursor.getDouble(cursor.getColumnIndex("amount"));

                wallet = new Wallet(iid, nname, aamount, accountid);
            }
            cursor.close();
            db.close();
        }
        return wallet;
    }

    @Override
    public Wallet findByWalletId(Integer walletId, String accountId) {
        Wallet wallet=null;
        SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from tb_wallet where walletId=? and accountId=?", new String[]{String.valueOf(walletId),accountId});

            while (cursor.moveToNext()) {
                String accountid = cursor.getString(cursor.getColumnIndex("accountId"));
                int iid = cursor.getInt(cursor.getColumnIndex("walletId"));
                String nname = cursor.getString(cursor.getColumnIndex("name"));
                double aamount=cursor.getDouble(cursor.getColumnIndex("amount"));

                wallet = new Wallet(iid, nname, aamount, accountid);
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
                int iid = (int) cursor.getLong(cursor.getColumnIndex("walletId"));
                String nname = cursor.getString(cursor.getColumnIndex("name"));
                double aamount=cursor.getDouble(cursor.getColumnIndex("amount"));
                Wallet wallet = new Wallet(iid, nname, aamount, accountid);
                list.add(wallet);
            }
            cursor.close();
            db.close();
            return list;
        }
        return null;
    }

}
