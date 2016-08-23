package com.code.space.androidlib.app;

/**
 * Created by shangxuebin on 16-5-26.
 */
public interface IAppInfo {
    String getRootUrl();
    String appendRoot(String url);
    String getProtocol();
    boolean gzip();
}
