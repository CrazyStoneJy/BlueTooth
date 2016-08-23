package com.code.space.javalib.pojo;

/**
 * Created by shangxuebin on 16-5-18.
 */
public enum  SuperClass {
    IMPLEMENT("implements"),
    EXTENDS("extends");

    public String getName(){
        return name;
    }
    String name;
    SuperClass(String name){
        this.name = name;
    }
}
