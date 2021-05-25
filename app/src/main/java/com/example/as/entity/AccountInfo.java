package com.example.as.entity;

import java.sql.Date;

public class AccountInfo {


    private String name;
    private String sex;
    private Date birthday;
    private String icon;
    private String accountId;

    public AccountInfo(){}

    public AccountInfo(String name, String sex, Date birthday, String icon, String accountId){
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        this.icon = icon;
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }


}

