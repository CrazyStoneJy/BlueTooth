package com.code.space.androidlib.utilities.reflect;

/**
 * Created by shangxuebin on 16-5-24.
 */
public class WrongGenericTypeException extends RuntimeException{
    public WrongGenericTypeException(){
        super("use anonymous inner class!");
    }
}
