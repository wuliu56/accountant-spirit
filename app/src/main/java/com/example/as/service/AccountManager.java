package com.example.as.service;

import com.example.as.entity.Account;

import java.util.ArrayList;

public class AccountManager {
    private ArrayList<Account> accountArray=new ArrayList<Account>();
    private Account currentAccount;
    private boolean state;

    boolean logUp(){//注册
        return true;
    }

    boolean logIn(Account account){//登录
        return true;
    }

    boolean logOut(){//登出
        return true;
    }

    boolean changePassword(){
        return true;
    }

    boolean getState() {return state;}
}

