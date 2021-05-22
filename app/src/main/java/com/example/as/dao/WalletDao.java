package com.example.as.dao;

import com.example.as.entity.Wallet;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public interface WalletDao {
    public boolean insert(Wallet wallet);
    public boolean deleteByName(String name,String accountId);
    public boolean updateWallet(String name,Wallet wallet);
    public Wallet findByName(String name);
    public ArrayList<Wallet> findByAccountId(String id);
}
