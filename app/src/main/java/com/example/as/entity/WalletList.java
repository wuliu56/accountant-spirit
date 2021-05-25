package com.example.as.entity;

import com.example.as.entity.Wallet;

import java.util.ArrayList;
import java.util.Iterator;

public class WalletList {

    private ArrayList<Wallet> walletArray = new ArrayList<Wallet>();
    private Wallet cursor = null;

    public WalletList(ArrayList<Wallet> walletArray){
        this.walletArray = walletArray;
    }

    public Wallet getWalletByIndex(int index){
        cursor = null;
        if(index >= 0&&index < getSize())
            cursor = walletArray.get(index);
        return cursor;
    }

    public int getSize(){
        return walletArray.size();
    }

}
