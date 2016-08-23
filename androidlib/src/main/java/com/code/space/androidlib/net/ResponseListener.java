package com.code.space.androidlib.net;

/**
 * Created by shangxuebin on 2016/5/17.
 */
public interface ResponseListener<RESPONSE> {
    void onNetResult(INetResult<RESPONSE> result);
}
