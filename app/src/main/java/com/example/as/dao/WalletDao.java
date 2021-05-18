package com.example.as.dao;

import com.example.as.entity.Wallet;

import java.util.List;

public interface WalletDao {
    public boolean insert(Wallet wallet);
    public boolean deleteByWalletId(String walletId);
    public boolean updateName(Wallet wallet, String name);
    public boolean updateAmount(Wallet wallet,double amount);
    public List<Wallet> findByWalletId(String id);
    public List<Wallet> findByAccountId(String id);
}
