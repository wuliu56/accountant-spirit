package com.example.as.dao;

import com.example.as.entity.AccountInfo;

public interface AccountInfoDao {
    public boolean insertAccountInfo(AccountInfo accountInfo);
    public boolean deleteByAccountId(String accountId);
    public boolean updateAccountInfo(AccountInfo accountinfo);
    public AccountInfo findByAccountId(String accountId);
}
