package com.example.as.dao;

import com.example.as.entity.AccountInfo;

import java.util.List;

public interface AccountInfoDao {
    public boolean insert(AccountInfo accountInfo);
    public boolean deleteByAccountId(String accountId);
    public boolean updateName(AccountInfo accountInfo,String name);
    public boolean updateSex(AccountInfo accountInfo,String sex);
    public boolean updateAge(AccountInfo accountInfo,int age);
    public boolean updatePosition(AccountInfo accountInfo,String position);
    public boolean updateIcon(AccountInfo accountInfo,String icon);
    public List<AccountInfo> findByAccountId(String accountId);
}
