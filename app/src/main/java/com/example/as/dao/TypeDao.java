package com.example.as.dao;

import com.example.as.entity.Type;

import java.util.List;

public interface TypeDao {
    public boolean insert(Type type);
    public boolean deleteByTypeId(String id);
    public List<Type> findAll(String accountId);
    public List<Type> findByCategory(String category);
}
