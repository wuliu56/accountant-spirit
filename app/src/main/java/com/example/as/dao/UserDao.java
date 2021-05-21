package com.example.as.dao;

import com.example.as.entity.Account;

public interface UserDao {
    public boolean insert(Account user);
    public boolean deleteByAccountId(String accountId);
    public boolean updatePwd(String username,String password);
    public boolean updateUsername(String accountId,String username);
    public boolean updateCurrency(String accountId,String currency);
    public Account findByUsername(String username);
}
