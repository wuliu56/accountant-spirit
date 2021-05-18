package com.example.as.dao;

import com.example.as.entity.Account;

import java.util.List;

public interface UserDao {
    public boolean insert(Account user);
    public boolean deleteByAccountId(String accountId);
    public boolean updatePwd(Account user,String password);
    public boolean updateUsername(Account user,String username);
    public List<Account> findByAccountId(String accountId);
}
