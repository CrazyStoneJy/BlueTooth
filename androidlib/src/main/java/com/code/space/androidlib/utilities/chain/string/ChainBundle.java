package com.code.space.androidlib.utilities.chain.string;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.code.space.androidlib.utilities.chain.AbstractChainBuilder;

/**
 * Created by shangxuebin on 2016/5/17.
 */
public class ChainBundle extends AbstractChainBuilder<Bundle> implements IStringBuilder<Bundle>{

    public ChainBundle() {
        setTarget(new Bundle());
    }

    @Override
    public ChainBundle put(String key, byte value) {
        getTarget().putByte(key,value);
        return this;
    }

    @Override
    public ChainBundle put(String key, char value) {
        getTarget().putChar(key,value);
        return this;
    }

    @Override
    public ChainBundle put(String key, short value) {
        getTarget().putShort(key,value);
        return this;
    }

    @Override
    public ChainBundle put(String key, int value) {
        getTarget().putInt(key,value);
        return this;
    }

    @Override
    public ChainBundle put(String key, long value) {
        getTarget().putLong(key,value);
        return this;
    }

    @Override
    public ChainBundle put(String key, float value) {
        getTarget().putFloat(key,value);
        return this;
    }

    @Override
    public ChainBundle put(String key, double value) {
        getTarget().putDouble(key,value);
        return this;
    }

    @Override
    public ChainBundle put(String key, boolean value) {
        getTarget().putBoolean(key,value);
        return this;
    }

    public Fragment setArguments(Fragment fragment){
        fragment.setArguments(build());
        return fragment;
    }
}
