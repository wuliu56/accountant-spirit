package com.example.as.entity;

public class Currency {

    private String name;
    private String symbol;

    //默认构造方法
    public Currency(){}

    public Currency(String name,String symbol){
        this.name=name;
        this.symbol=symbol;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }
}
