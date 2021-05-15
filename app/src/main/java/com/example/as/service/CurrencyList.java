package com.example.as.service;

import com.example.as.entity.Currency;

import java.util.ArrayList;
import java.util.Iterator;

public class CurrencyList {

    private ArrayList<Currency> currencySet = new ArrayList<Currency>();
    Currency temp = new Currency();

    public Currency queryCurrencyByName(String name) {
        Iterator<Currency> it = currencySet.iterator();
        while(it.hasNext()){
            temp=it.next();
            if(temp.getName().equals(name)){
                return temp;
            }
        }
        return null;
    }

    public Currency queryCurrencyByIndex(int index) {
        return currencySet.get(index);
    }

    public int getSize() {
        return currencySet.size();
    }

}



