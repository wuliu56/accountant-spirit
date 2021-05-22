package com.example.as.entity;

import com.example.as.entity.Currency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CurrencyList {


    private HashMap<String,Currency> currencyHashMap = new HashMap<String,Currency>();
    int size;

    public CurrencyList() {
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
    }

    public Currency getCurrencyByName(String name){
        return currencyHashMap.get(name);
    }

    public Currency getCurrencyByIndex(int index) {
        Iterator iter = currencyHashMap.entrySet().iterator();
        Map.Entry entry = null;
        Currency val = null;

        if(index>0&&index<=getSize()) {
            for (int i = 0; i < index; i++) {
                entry = (Map.Entry) iter.next();
            }
            val = (Currency) entry.getValue();
        }
        return val;
    }

    public int getSize() {
        return currencyHashMap.size();
    }
}



