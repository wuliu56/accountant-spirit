package com.example.as.dao;

import com.example.as.entity.Type;

import java.util.ArrayList;
import java.util.List;

public interface TypeDao {
    public boolean insert(Type type);
    public boolean deleteByName(String name,String accountId);
    public boolean updateType(String name,Type type);
    public Type findByName(String name,String accountId);
    public Type findByTypeId(Integer typeId,String accountId);
    public ArrayList<Type> findAll(String accountId);
    public ArrayList<Type> findByCategory(String category);
}
