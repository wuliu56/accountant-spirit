package com.example.as.entity;

import com.example.as.entity.Wallet;

import java.util.ArrayList;
import java.util.Iterator;

public class WalletList {


    private ArrayList<Wallet> walletArray=new ArrayList<Wallet>();
    int size;


    public void setWalletArray(ArrayList<Wallet> walletArray) {
        this.walletArray = walletArray;
    }

    public Wallet getWalletByIndex(int index){
        return walletArray.get(index);
    }

    public int getSize(){
        return walletArray.size();
    }

}
