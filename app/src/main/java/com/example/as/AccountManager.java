package com.example.as;

public class AccountManager {
   private String accountId;

    public AccountManager(){}

    public AccountManager(String accountId){
        this.accountId = accountId;
    }

    public boolean logUp(String username,String password){//注册
        return true;
    }

    public boolean logIn(String username,String password){//登录
        //如果账户密码正确，登录，将accountId作为全局变量放入Application，并且返回true；否则返回false
        return true;
    }

    public boolean logOut(){//登出
        return true;
    }

    public boolean changePassword(){
        return true;
    }
}

