package com.example.as.entity;

import com.example.as.entity.Currency;

import java.util.ArrayList;
import java.util.Iterator;

public class CurrencyList {


    private ArrayList<Currency> currencyArray = new ArrayList<Currency>();
    int size;

    public CurrencyList(){
        currencyArray.add(new Currency("人民币","￥"));
        currencyArray.add(new Currency("美元","$"));
        currencyArray.add(new Currency("欧元","€"));
        currencyArray.add(new Currency("英镑","￡"));
        currencyArray.add(new Currency("澳元","A$"));
        currencyArray.add(new Currency("日元","¥"));
        currencyArray.add(new Currency("加元","C$"));
        currencyArray.add(new Currency("香港港元","HK$"));
        currencyArray.add(new Currency("台币","NT$"));
        currencyArray.add(new Currency("韩元","₩"));
    }



    public Currency getCurrencyByIndex(int index) {
        return currencyArray.get(index);
    }

    public int getSize() {
        return currencyArray.size();
    }

}



