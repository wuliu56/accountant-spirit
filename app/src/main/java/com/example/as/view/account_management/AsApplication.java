package com.example.as.view.account_management;

import android.app.Application;

public class AsApplication extends Application {
    private String accountId = "";

    public void setAccountId(String accountId){
        this.accountId = accountId;
    }

    public String getAccountId(){
        return this.accountId;
    }
}
