package com.code.space.androidlib.net;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by shangxuebin on 16-6-7.
 */
public interface INetResult<RESPONSE> {
    boolean success();
    int id();
    RESPONSE response();
    Call okCall();
    Response okResponse();
    Exception error();
}
