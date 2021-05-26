package com.example.as.service;

import android.app.Activity;
import android.content.Context;

import com.example.as.R;
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
    private String accountId = AsApplication.getAccountId();
    private CurrencyList currencylist = new CurrencyList();
    private WalletList walletList = null;
    private TypeList typeList = null;

    private UserDaoImpl userdaoimpl = new UserDaoImpl(AsApplication.getContext());
    private AccountInfoDaoImpl accountinfodaoimpl = new AccountInfoDaoImpl(AsApplication.getContext());
    private WalletDaoImpl walletdaoimpl = new WalletDaoImpl(AsApplication.getContext());
    private TypeDaoImpl typedaoimpl = new TypeDaoImpl(AsApplication.getContext());

    //构造方法
    public AccountManager(){}

    public AccountManager(String accountId){
        this.accountId = accountId;
        walletList = new WalletList(walletdaoimpl.findByAccountId(accountId));
        typeList = new TypeList(typedaoimpl.findAll(accountId));
    }



    //登录注册
    public boolean logUp(String username,String password){
        //注册

        Account tempAccount = userdaoimpl.findByUsername(username);

        //首先判断当前用户名是否已经被注册
        if(tempAccount == null){
            //用户名还未注册,写入sqlite

            String accountId = String.valueOf(userdaoimpl.getSize());
            //插入user记录
            Account newAccount = new Account(accountId,username,
                    password, currencylist.getCurrencyByName("人民币"));
            userdaoimpl.insert(newAccount);

            //插入wallet记录，默认为现金，资产额0；支付宝，资产额0；微信，资产额0；银行卡，资产额0
            newWallet(new Wallet(null, "现金", 0.0, accountId));
            newWallet(new Wallet(null, "支付宝", 0.0, accountId));
            newWallet(new Wallet(null, "微信", 0.0, accountId));
            newWallet(new Wallet(null, "银行卡", 0.0, accountId));

            //插入默认类型
            Context context = AsApplication.getContext();
            ArrayList<String[]> typeNameList = new ArrayList<String[]>();
            typeNameList.add(context.getResources().getStringArray(R.array.category1));
            typeNameList.add(context.getResources().getStringArray(R.array.category2));
            typeNameList.add(context.getResources().getStringArray(R.array.category3));
            typeNameList.add(context.getResources().getStringArray(R.array.category4));
            typeNameList.add(context.getResources().getStringArray(R.array.category5));
            typeNameList.add(context.getResources().getStringArray(R.array.category6));
            typeNameList.add(context.getResources().getStringArray(R.array.category7));
            typeNameList.add(context.getResources().getStringArray(R.array.category8));
            typeNameList.add(context.getResources().getStringArray(R.array.category9));
            typeNameList.add(context.getResources().getStringArray(R.array.category10));
            for(int i = 0; i < typeNameList.size(); i++){
                String[] typeNameArr = typeNameList.get(i);
                for(int j = 1; j < typeNameArr.length; j++){
                    typedaoimpl.insert(new Type(typeNameArr[j], typeNameArr[0], accountId));
                }
            }
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



    //修改账户密码和信息
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
        if(accountinfodaoimpl.findByAccountId(accountId) == null)
            accountinfodaoimpl.insert(accountInfo);
        else{
            accountinfodaoimpl.updateAccountInfo(accountInfo);
        }
    }



    //管理账户的当前货币，钱包清单以及类型清单
    public Currency getCurrenctCurrency() {
        return userdaoimpl.findByAccountId(accountId).getCurrency();
    }

    public void setCurrentCurrency(Currency currency) {
        userdaoimpl.updateCurrency(accountId,currency.getName());
    }

    public CurrencyList getCurrencyList(){ return currencylist; }

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
        return walletdaoimpl.findByName(name, accountId);
    }

    public WalletList getWalletList(){
        return walletList;
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

    public ArrayList<Type> queryTypeListByCategory(String category){
        return typedaoimpl.findByCategory(category);
    }

    public TypeList getTypeList() { return typeList; }
}
