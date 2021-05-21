package com.example.as.dao.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.as.dao.TypeDBOpenHelper;
import com.example.as.dao.TypeDao;
import com.example.as.entity.Type;

import java.util.ArrayList;

public class TypeDaoImpl implements TypeDao {
    public final TypeDBOpenHelper dbOpenHelper;

    public TypeDaoImpl() {
        dbOpenHelper = new TypeDBOpenHelper(null, null, null, 1);
    }

    @Override
    public boolean insert(Type type) {
        ContentValues values = new ContentValues();
        values.put("accountId",type.getAccountId());
        values.put("typeId", type.getId());
        values.put("name", type.getName());
        values.put("category", type.getCategory());
        dbOpenHelper.getWritableDatabase().insert("tb_type", null, values);
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }

    @Override
    public boolean deleteByName(String name,String accountId) {
        dbOpenHelper.getWritableDatabase().execSQL("delete from tb_type where name=? and accountId=?", new Object[]{name,accountId});
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }

    @Override
    public boolean updateType(String name,Type type) {
        dbOpenHelper.getWritableDatabase().execSQL("update tb_type set category=? where mame=? and typeId=?", new Object[]{type.getCategory(),name,type.getId()});
        dbOpenHelper.getWritableDatabase().execSQL("update tb_type set name=? where mame=? and typeId=?", new Object[]{type.getName(),name,type.getId()});
        dbOpenHelper.getWritableDatabase().close();
        return true;
    }

    @Override
    public Type findByName(String name,String accountId){
        SQLiteDatabase db=dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from tb_type where name=? and accountId=?", new String[]{name,accountId});
            if(cursor.moveToNext()==false)return null;
            while (cursor.moveToNext()) {
                String accountid = cursor.getString(cursor.getColumnIndex("accountId"));
                String iid = cursor.getString(cursor.getColumnIndex("typeId"));
                String nname = cursor.getString(cursor.getColumnIndex("name"));
                String ccategory=cursor.getString(cursor.getColumnIndex("category"));
                Type type=new Type();
                type.setAccountId(accountid);
                type.setId(iid);
                type.setName(nname);
                type.setCategory(ccategory);
                return type;
            }
            cursor.close();
            db.close();
        }
        return null;
    }

    @Override
    public ArrayList<Type> findAll(String accountId) {
        ArrayList<Type> list = new ArrayList<>();
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from tb_type where accountId=?", new String[]{accountId});
            if(cursor.moveToNext()==false)return null;
            while (cursor.moveToNext()) {
                String accountid = cursor.getString(cursor.getColumnIndex("accountId"));
                String iid = cursor.getString(cursor.getColumnIndex("typeId"));
                String nname = cursor.getString(cursor.getColumnIndex("name"));
                String ccategory = cursor.getString(cursor.getColumnIndex("category"));
                Type type = new Type();
                type.setAccountId(accountid);
                type.setId(iid);
                type.setName(nname);
                type.setCategory(ccategory);
                list.add(type);
            }
            cursor.close();
            db.close();
        }
        return list;
    }

    @Override
    public ArrayList<Type> findByCategory(String category) {
        ArrayList<Type> list = new ArrayList<>();
        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        if (db.isOpen()) {
            Cursor cursor = db.rawQuery("select * from tb_type where category=?", new String[]{category});
            if(cursor.moveToNext()==false)return null;
            while (cursor.moveToNext()) {
                String accountid = cursor.getString(cursor.getColumnIndex("accountId"));
                String iid = cursor.getString(cursor.getColumnIndex("typeId"));
                String nname = cursor.getString(cursor.getColumnIndex("name"));
                String ccategory = cursor.getString(cursor.getColumnIndex("category"));
                Type type = new Type();
                type.setAccountId(accountid);
                type.setId(iid);
                type.setName(nname);
                type.setCategory(ccategory);
                list.add(type);
            }
            cursor.close();
            db.close();
        }
        return list;
    }
}
