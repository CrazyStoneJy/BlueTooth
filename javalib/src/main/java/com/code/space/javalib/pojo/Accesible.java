package com.code.space.javalib.pojo;

/**
 * Created by shangxuebin on 16-5-18.
 */
public enum Accesible{

    PUBLIC("public"),
    DEFUALT(""),
    PROTECTED("protected"),
    PRIVATE("private");

    public String getName(){
        return name;
    }
    String name;
    Accesible(String name){
        this.name = name;
    }
}
