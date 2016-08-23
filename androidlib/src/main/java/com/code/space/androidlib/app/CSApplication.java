package com.code.space.androidlib.app;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.os.Handler;
import android.os.Looper;

import com.code.space.androidlib.utilities.utils.AppUtils;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * The base application class
 */
public abstract class CSApplication<INFO extends IAppInfo> extends Application{

    /**
     * a static reference to store the application instance
     */
    private static CSApplication thiz;
    /**
     * a singleton main thread handler
     */
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    /**
     * appInfo
     * @see IAppInfo
     */
    protected INFO mAppInfo;

    /**
     * default constructor
     */
    public CSApplication() {
        thiz = this;
    }

    /**
     * initial the application environment
     */
    @Override
    public void onCreate() {

        super.onCreate();

        if(thiz==null) thiz=this;

        //标记时debug还是release的标记
        ApplicationInfo applicationInfo = getApplicationInfo();
        AppUtils.debugging = (applicationInfo.flags&ApplicationInfo.FLAG_DEBUGGABLE)!=0;

        //初始化appinf
        mAppInfo = initAppInfo();

        //fresco
        Fresco.initialize(this);
    }

    /**
     * a public method to get the static application instance reference
     * @return the application instance
     */
    public static CSApplication getApplication(){
        return thiz;
    }

    /**
     * a public method to ge the singleton handler instance
     * @return a main thread handler
     */
    public Handler mainHandler(){
        return mainHandler;
    }

    public INFO getAppInfo(){
        return mAppInfo;
    }

    public abstract INFO initAppInfo();
}
