package com.example.as.entity;


import com.example.as.BillList;
import com.example.as.MonthlyBudgetList;

public class Account {

    private String Id;
    private String password;
    private AccountInfo accountInfo;
    private Currency currency;
    private WalletList walletList;
    private BillList billList;
    private MonthlyBudgetList monthlyBudgetList;
    private TypeList typeList;

    public AccountInfo getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }

    public Currency getCurrency(){
        return currency;
    }

    public void setCurrency(Currency currency){
        this.currency=currency;
    }

    public WalletList getWalletList(){
        return walletList;
    }

    public BillList getBillList(){
        return billList;
    }

    public MonthlyBudgetList getMonthlyBudgetList() {
        return monthlyBudgetList;
    }

    public TypeList getTypeList() {
        return typeList;
    }

}

