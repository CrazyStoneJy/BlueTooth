package com.code.space.androidlib.utilities.chain.string;

import com.code.space.androidlib.utilities.chain.IChainBuilder;

/**
 * Created by shangxuebin on 2016/5/17.
 */
public interface IStringBuilder<TARGET> extends IChainBuilder<TARGET>{
    IStringBuilder<TARGET> put(String key, byte value);
    IStringBuilder<TARGET> put(String key, char value);
    IStringBuilder<TARGET> put(String key, short value);
    IStringBuilder<TARGET> put(String key, int value);
    IStringBuilder<TARGET> put(String key, long value);
    IStringBuilder<TARGET> put(String key, float value);
    IStringBuilder<TARGET> put(String key, double value);
    IStringBuilder<TARGET> put(String key, boolean value);
}
