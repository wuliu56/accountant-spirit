package com.example.as.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.as.dao.TypeDBOpenHelper;
import com.example.as.dao.TypeDao;
import com.example.as.entity.Type;
import com.example.as.entity.Wallet;

import java.util.ArrayList;
import java.util.List;

public class TypeDaoImpl implements TypeDao {
    public final TypeDBOpenHelper dbOpenHelper;

    public TypeDaoImpl(Context context) {
        dbOpenHelper = new TypeDBOpenHelper(context, "type.db", null, 1);
    }

    @Override
    public boolean insert(Type type) {
        ContentValues values = new ContentValues();
        values.put("accountId",type.getAccountId());
        values.put("typeId", type.getId());
        values.put("name", type.getName());
        values.put("category", type.getCategory());
        dbOpenHelper.getWritableDatabase().insert("tb_type",null,values);
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }

    @Override
    public boolean deleteByName(String name,String accountId) {
        dbOpenHelper.getWritableDatabase().execSQL("delete from tb_type where name=? and accountId=?", new String[]{name,accountId});
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }

    @Override
    public boolean updateType(String name,Type type) {
        dbOpenHelper.getWritableDatabase().execSQL("update tb_type set category=? where name=? and typeId=?", new String[]{type.getCategory(),name, String.valueOf(type.getId())});
        dbOpenHelper.getWritableDatabase().execSQL("update tb_type set name=? where name=? and typeId=?", new String[]{type.getName(),name, String.valueOf(type.getId())});
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }

    @Override
    public Type findByName(String name,String accountId){
        Type type=null;
        SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from tb_type where name=? and accountId=?", new String[]{name,accountId});

            while (cursor.moveToNext()) {
                String accountid = cursor.getString(cursor.getColumnIndex("accountId"));
                int iid = cursor.getInt(cursor.getColumnIndex("typeId"));
                String nname = cursor.getString(cursor.getColumnIndex("name"));
                String ccategory=cursor.getString(cursor.getColumnIndex("category"));

                type = new Type(iid, nname, ccategory, accountid);
            }
            cursor.close();
            db.close();
        }
        return type;
    }

    @Override
    public Type findByTypeId(Integer typeId,String accountId){
        Type type=null;
        SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from tb_type where typeId=? and accountId=?", new String[]{String.valueOf(typeId),accountId});

            while (cursor.moveToNext()) {
                String accountid = cursor.getString(cursor.getColumnIndex("accountId"));
                int iid = cursor.getInt(cursor.getColumnIndex("typeId"));
                String nname = cursor.getString(cursor.getColumnIndex("name"));
                String ccategory=cursor.getString(cursor.getColumnIndex("category"));

                type = new Type(iid, nname, ccategory, accountid);
            }
            cursor.close();
            db.close();
        }
        return type;
    }

    @Override
    public ArrayList<Type> findAll(String accountId) {
        ArrayList<Type> list = new ArrayList<Type>();
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from tb_type where accountId=?", new String[]{accountId});

            while (cursor.moveToNext()) {
                String accountid = cursor.getString(cursor.getColumnIndex("accountId"));
                int iid = cursor.getInt(cursor.getColumnIndex("typeId"));
                String nname = cursor.getString(cursor.getColumnIndex("name"));
                String ccategory = cursor.getString(cursor.getColumnIndex("category"));

                Type type = new Type(iid, nname, ccategory, accountid);
                list.add(type);
            }
            cursor.close();
            db.close();
            return list;
        }
        return null;
    }

    @Override
    public ArrayList<Type> findByCategory(String category) {
        ArrayList<Type> list = new ArrayList<Type>();
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from tb_type where category=?", new String[]{category});

            while (cursor.moveToNext()) {
                String accountid = cursor.getString(cursor.getColumnIndex("accountId"));
                int iid = cursor.getInt(cursor.getColumnIndex("typeId"));
                String nname = cursor.getString(cursor.getColumnIndex("name"));
                String ccategory = cursor.getString(cursor.getColumnIndex("category"));

                Type type = new Type(iid, nname, ccategory, accountid);
                list.add(type);
            }
            cursor.close();
            db.close();
            return list;
        }
        return null;
    }
}
