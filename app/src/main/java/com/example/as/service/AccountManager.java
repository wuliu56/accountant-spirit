package com.example.as.service;

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

import java.util.ArrayList;

public class AccountManager {
    private ArrayList<Account> accountArray=new ArrayList<Account>();
    private UserDaoImpl userdaoimpl;
    private Account currentaccount;
    private String accountId;
    private AccountInfoDaoImpl accountinfodaoimpl;
    private WalletDaoImpl walletdaoimpl;
    private TypeDaoImpl typedaoimpl;
    private static int count=0;
    private CurrencyList currencylist;

    boolean logUp(String username,String password){//注测
        //首先判断当前id是否已经被注册
        Account tempaccount=new Account();
        tempaccount=userdaoimpl.findByUsername(username);
        if(tempaccount==null){
            Account newaccount =new Account();
            newaccount.setUsername(username);
            newaccount.setPassword(password);
            count++;
            newaccount.setAccountId(Integer.toString(count));
            userdaoimpl.insert(newaccount);
            return true;
        }
        else return false;
    }

    boolean logIn(String username,String password){//登录
        Account tempaccount=new Account();
        tempaccount=userdaoimpl.findByUsername(username);
        if(tempaccount==null){//账号错误
            System.out.println("账号错误");
           return false;
        }
        else if(tempaccount.getPassword().equals(password)){
            currentaccount=tempaccount;
            accountId=tempaccount.getAccountId();
            return true;
        }else{
            System.out.println("密码错误");
            return false;
        }
    }

    boolean logOut(){//登出  ???
        accountId=null;
        return true;
    }

    boolean changePassword(String username,String oldpassword,String newpassword){
        Account tempaccount=new Account();
        tempaccount=userdaoimpl.findByUsername(username);
        if(tempaccount==null){//账号错误
            System.out.println("账号错误");
            return false;
        }
        else if(tempaccount.getPassword().equals(oldpassword)){
            userdaoimpl.updatePwd(username,newpassword);
            return true;
        }else{
            System.out.println("密码错误");
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
        return currentaccount.getCurrency();
    }

    public void setCurrentCurrency(Currency currency) {
        currentaccount.setCurrency(currency);
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
