package com.code.space.androidlib.view.finder;

public class WrongViewTypeException extends RuntimeException{

    public WrongViewTypeException(Object view) {
        super("Wrong view object:"+(view==null?"null":view.getClass().getSimpleName())+" object");
    }


}
