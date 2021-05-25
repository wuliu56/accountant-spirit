package com.example.as.entity;

public class Type {

    private Integer id;
    private String name;//具体种类的名称
    private String category;//大分类
    private String accountId;

    //默认构造方法
    public Type(){
        this.id = null;
    }

    //用于插入时构造
    public Type(String name, String category, String accountId){
        this.id = null;
        this.name = name;
        this.category = category;
        this.accountId = accountId;
    }

    //用于查询时构造
    public Type(Integer id, String name, String category, String accountId){
        this.id = id;
        this.name = name;
        this.category = category;
        this.accountId = accountId;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
