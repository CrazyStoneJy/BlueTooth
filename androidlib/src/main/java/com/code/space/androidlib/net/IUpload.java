package com.code.space.androidlib.net;

import com.code.space.androidlib.utilities.ICheckLegal;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by shangxuebin on 16-6-15.
 */
public interface IUpload extends ICheckLegal{
    void addToBuilder(String key, MultipartBody.Builder builder);
}
