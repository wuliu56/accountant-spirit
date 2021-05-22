package com.example.as.dao;

import com.example.as.entity.AccountInfo;

import java.sql.Date;
import java.util.ArrayList;

public interface AccountInfoDao {
    public boolean insert(AccountInfo accountInfo);
    public boolean deleteByAccountId(String accountId);
    public boolean updateAccountInfo(AccountInfo accountinfo);
    public AccountInfo findByAccountId(String accountId);
}
