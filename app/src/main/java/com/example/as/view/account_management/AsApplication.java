package com.example.as.view.account_management;

import android.app.Application;
import android.content.Context;

public class AsApplication extends Application {
    private static String accountId = null;
    private static Context context = null;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public AsApplication(){}

    public AsApplication(String inAccountId){accountId = inAccountId;}

    public static void setAccountId(String inAccountId){
        accountId = inAccountId;
    }

    public static String getAccountId(){ return accountId; }

    public static Context getContext(){ return context;}
}
