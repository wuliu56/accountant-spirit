package com.example.as;

public class Type {

    private String name;//具体种类的名称
    private String category;//大分类

    public Type(){}

    public Type(String name,String category){
        this.name=name;
        this.category=category;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

}
