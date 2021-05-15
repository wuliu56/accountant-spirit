package com.example.as;

import java.util.ArrayList;
import java.util.Iterator;

public class WalletList {

    private ArrayList<Wallet> wallets=new ArrayList<Wallet>();
    private Wallet temp=new Wallet();

    public void newWallet(String name,double amount){
        wallets.add(new Wallet(name,amount));
    }

    public void deleteWallet(String name){
        Iterator<Wallet> it=wallets.iterator();
        while(it.hasNext()){
            temp=it.next();
            if(temp.getName()==name){
                int index=wallets.indexOf(temp);
                wallets.remove(index);//好像可以直接删
                break;
            }
        }
    }

    public double getWalletAmount(String name){
        Iterator<Wallet> it=wallets.iterator();
        while(it.hasNext()){
            temp=it.next();
            if(temp.getName()==name){
                return temp.getAmount();
            }
        }
        return 0;
    }

    public void setWalletAmount(String name,double amount){
        Iterator<Wallet> it=wallets.iterator();
        while(it.hasNext()){
            temp=it.next();
            if(temp.getName()==name){
                temp.setAmount(amount);
                break;
            }
        }
    }

    public Wallet getWalletByIndex(int index){
        return wallets.get(index);
    }

    public int getSize(){
        return wallets.size();
    }

}
