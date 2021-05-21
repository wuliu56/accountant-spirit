package com.example.as.entity;

import com.example.as.entity.Type;

import java.util.ArrayList;
import java.util.Iterator;

public class TypeList {


    private ArrayList<Type> typeArray=new ArrayList<Type>();
    private int size;


    public void setTypeArray(ArrayList<Type> typeArray) {
        this.typeArray = typeArray;
    }

    public Type getTypeByIndex(int index){
        return typeArray.get(index);
    }

    public int getSize(){
        return typeArray.size();
    }

}
