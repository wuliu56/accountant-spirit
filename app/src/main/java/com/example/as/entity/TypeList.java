package com.example.as.entity;

import com.example.as.entity.Type;

import java.util.ArrayList;
import java.util.Iterator;

public class TypeList {

    private ArrayList<Type> typeArray = new ArrayList<Type>();
    private Type cursor = null;

    public TypeList(ArrayList<Type> typeArray){
        this.typeArray = typeArray;
    }

    public Type getTypeByIndex(int index){
        cursor = null;
        if(index >= 0&&index < getSize())
            cursor = typeArray.get(index);
        return cursor;
    }

    public int getSize(){
        return typeArray.size();
    }

}
