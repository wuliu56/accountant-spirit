package com.example.as.service;

import com.example.as.entity.Account;

import java.util.ArrayList;

public class AccountManager {
    private ArrayList<Account> accountArray=new ArrayList<Account>();
    private Account currentAccount;
    private boolean state;

    public boolean logUp(){//注册
        return true;
    }

    public boolean logIn(Account account){//登录
        return true;
    }

    public boolean logOut(){//登出
        return true;
    }

    public boolean changePassword(){
        return true;
    }

    public boolean getState() {return state;}
}

