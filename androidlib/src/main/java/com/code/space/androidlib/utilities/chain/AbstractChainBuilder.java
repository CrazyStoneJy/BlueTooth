package com.code.space.androidlib.utilities.chain;

/**
 * Created by shangxuebin on 16-6-15.
 */
public class AbstractChainBuilder<T> implements IChainBuilder<T> {

    protected T t;

    protected void setTarget(T t){
        this.t = t;
    }

    protected T getTarget(){
        return t;
    }

    @Override
    public T build() {
        return t;
    }
}
