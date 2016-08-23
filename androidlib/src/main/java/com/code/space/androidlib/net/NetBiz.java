package com.code.space.androidlib.net;

import com.code.space.androidlib.utilities.utils.ContextUtils;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;

/**
 * Created by shangxuebin on 16-4-7.
 */
public class NetBiz {

    public static final OkHttpClient httpClient = new OkHttpClient();

    public static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    public static OkHttpClient getClient(){
        return httpClient;
    }

    public static String checkUrl(String url){
        return checkUrl(url, ContextUtils.getApplication().getAppInfo().getProtocol());
    }

    public static String checkUrl(String url,String head){
        if(!head.endsWith("://")) head+="://";
        if (!url.regionMatches(true, 0, head, 0, head.length())) {
            url = head+url;
        }
        return url;
    }

}


