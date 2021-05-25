package com.example.as.entity;

import com.example.as.entity.Currency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CurrencyList {


    private ArrayList<Currency> currencyArrayList = new ArrayList<Currency>();
    private Currency cursor;
    int size;

    public CurrencyList() {
        currencyArrayList.add(new Currency("人民币", "￥"));
        currencyArrayList.add(new Currency("香港港元", "HK$"));
        currencyArrayList.add(new Currency("台币", "NT$"));
        currencyArrayList.add(new Currency("美元", "$"));
        currencyArrayList.add(new Currency("欧元", "€"));
        currencyArrayList.add(new Currency("英镑", "￡"));
        currencyArrayList.add(new Currency("日元", "¥"));
        currencyArrayList.add(new Currency("韩元", "₩"));
        currencyArrayList.add(new Currency("澳元", "A$"));
        currencyArrayList.add(new Currency("加元", "C$"));
    }

    public Currency getCurrencyByName(String name){
        HashMap<String, Currency> currencyHashMap = new HashMap<String, Currency>();
        currencyHashMap.put("人民币",new Currency("人民币", "￥"));
        currencyHashMap.put("美元",new Currency("美元", "$"));
        currencyHashMap.put("欧元",new Currency("欧元", "€"));
        currencyHashMap.put("英镑",new Currency("英镑", "￡"));
        currencyHashMap.put("澳元",new Currency("澳元", "A$"));
        currencyHashMap.put("日元",new Currency("日元", "¥"));
        currencyHashMap.put("加元",new Currency("加元", "C$"));
        currencyHashMap.put("香港港元",new Currency("香港港元", "HK$"));
        currencyHashMap.put("台币",new Currency("台币", "NT$"));
        currencyHashMap.put("韩元",new Currency("韩元", "₩"));
        return currencyHashMap.get(name);
    }

    public Currency getCurrencyByIndex(int index) {
        cursor = null;
        if(index>=0&&index<getSize())
            cursor = currencyArrayList.get(index);
        return cursor;
    }

    public int getSize() {
        return currencyArrayList.size();
    }
}



