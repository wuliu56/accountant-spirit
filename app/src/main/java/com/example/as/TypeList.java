package com.example.as;

import java.util.ArrayList;
import java.util.Iterator;

public class TypeList {

    private int size;
    private ArrayList<Type> types=new ArrayList<Type>(size);
    private ArrayList<Type> targets=new ArrayList<Type>(size);
    Type temp=new Type();

    public void newType(String name,String category){
        types.add(new Type(name,category));
    }

    public void deleteType(String name){
        Iterator<Type> it=types.iterator();
        while(it.hasNext()){
            temp=it.next();
            if(temp.getName().equals(name)){
                int index=types.indexOf(temp);
                types.remove(index);
                break;
            }
        }
    }

    public ArrayList<Type> getTypesByCategory(String category){//types[]数组中属于category类的Type 对象
        // 放在一个新的Type数组中并返回该数组
        Iterator<Type> it=types.iterator();
        while(it.hasNext()) {
            temp = it.next();
            if (temp.getCategory().equals(category)) {
                targets.add(temp);
            }
        }
        return targets;
    }

    public int getSize(){
        size=types.size();
        return size;
    }

}
