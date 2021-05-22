package com.example.as.service;

import android.app.Activity;
import android.widget.Toast;

import com.example.as.dao.impl.AccountInfoDaoImpl;
import com.example.as.dao.impl.TypeDaoImpl;
import com.example.as.dao.impl.UserDaoImpl;
import com.example.as.dao.impl.WalletDaoImpl;
import com.example.as.entity.Account;
import com.example.as.entity.AccountInfo;
import com.example.as.entity.Currency;
import com.example.as.entity.CurrencyList;
import com.example.as.entity.Type;
import com.example.as.entity.TypeList;
import com.example.as.entity.Wallet;
import com.example.as.entity.WalletList;
import com.example.as.view.account_management.AsApplication;

import java.util.ArrayList;

public class AccountManager {
    private String accountId;
    private CurrencyList currencylist = new CurrencyList();

    private UserDaoImpl userdaoimpl = new UserDaoImpl(AsApplication.getContext());
    private AccountInfoDaoImpl accountinfodaoimpl;
    private WalletDaoImpl walletdaoimpl;
    private TypeDaoImpl typedaoimpl;

    public AccountManager(){}

    public AccountManager(String accountId){
        this.accountId = accountId;
    }

    public boolean logUp(String username,String password){
        //注册

        Account tempAccount = userdaoimpl.findByUsername(username);

        //首先判断当前用户名是否已经被注册
        if(tempAccount == null){
            //用户名还未注册,写入sqlite
            Account newAccount = new Account();
            newAccount.setUsername(username);
            newAccount.setPassword(password);
            newAccount.setCurrency(currencylist.getCurrencyByName("人民币"));
            newAccount.setAccountId(String.valueOf(userdaoimpl.getSize()));
            userdaoimpl.insert(newAccount);
            return true;
        } else //用户名已被注册
            return false;
    }

    public int logIn(String username,String password){
        //登录
        Account tempAccount = userdaoimpl.findByUsername(username);

        if(tempAccount == null){
            //用户名错误
           return 1;
        } else if(password.equals(tempAccount.getPassword())){
            //用户名和密码正确,在AsApplication中设置全局变量accountId
            accountId = tempAccount.getAccountId();
            AsApplication.setAccountId(accountId);
            return 2;
        } else{
            //用户名存在，密码错误
            return 3;
        }
    }

    public boolean logOut(){
        //登出
        accountId = null;
        Activity activity = new Activity();
        AsApplication asApplication = (AsApplication) activity.getApplication();
        asApplication.setAccountId(accountId);
        return true;
    }

    public boolean changePassword(String oldPassword,String newPassword){
        Account tempAccount = userdaoimpl.findByAccountId(accountId);
        if(tempAccount.getPassword().equals(oldPassword)){
            //密码正确
            userdaoimpl.updatePwd(tempAccount.getUsername(),newPassword);
            return true;
        }else{
            //密码错误
            return false;
        }
    }

    public AccountInfo getAccountInfo() {
        return accountinfodaoimpl.findByAccountId(accountId);
    }

    public void setAccountInfo(AccountInfo accountInfo) {
        if(accountInfo.getAccountId().equals(accountId)){
            accountinfodaoimpl.updateAccountInfo(accountInfo);
        }
    }

    public Currency getCurrenctCurrency() {
        return userdaoimpl.findByAccountId(accountId).getCurrency();
    }

    public void setCurrentCurrency(Currency currency) {
        userdaoimpl.updateCurrency(accountId,currency.getName());
    }

    public CurrencyList getCurrencyList(){//所有用户的货币类型列表还是单纯的货币类型列表
        return currencylist;
    }

    public void newWallet(Wallet wallet){
        walletdaoimpl.insert(wallet);
    }

    public void deleteWallet(String name){
        walletdaoimpl.deleteByName(name,accountId);
    }

    public void setWallet(String name,Wallet wallet){
        if(wallet.getAccountId().equals(accountId)){
            walletdaoimpl.updateWallet(name,wallet);
        }
    }

    public Wallet queryWalletByName(String name){
        return walletdaoimpl.findByName(name);
    }

    public WalletList getWalletList(){
        WalletList walletlist=new WalletList();
        walletlist.setWalletArray(walletdaoimpl.findByAccountId(accountId));
        return walletlist;
    }

    public void newType(Type type){
        typedaoimpl.insert(type);
    }

    public void deleteByType(String name){
        typedaoimpl.deleteByName(name,accountId);
    }

    public void setType(String name,Type type){
        typedaoimpl.updateType(name,type);
    }

    public Type queryTypeByName(String name){
        return typedaoimpl.findByName(name,accountId);
    }

    public TypeList getTypeList(){
        TypeList typelist=new TypeList();
        typelist.setTypeArray(typedaoimpl.findAll(accountId));
        return typelist;
    }
}
