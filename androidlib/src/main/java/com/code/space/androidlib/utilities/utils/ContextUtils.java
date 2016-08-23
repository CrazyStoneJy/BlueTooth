package com.code.space.androidlib.utilities.utils;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Handler;

import com.code.space.androidlib.app.CSApplication;


/**
 * Created by shangxuebin on 16-4-7.
 */
public class ContextUtils {

    public static Handler mainHandler(){
        return ContextUtils.<CSApplication>getApplication(null).mainHandler();
    }

    @SuppressWarnings("unchecked")
    public static <APPLICATION extends CSApplication>APPLICATION getApplication(){
        return (APPLICATION) CSApplication.getApplication();

    }

    @SuppressWarnings("unchecked")
    public static <APPLICATION extends Application>APPLICATION getApplication(Context context){
        if(context==null)return (APPLICATION) CSApplication.getApplication();
        else return (APPLICATION)context.getApplicationContext();

    }


    public static AssetManager getAsset(Context context){
        return ContextUtils.getApplication(context).getAssets();
    }

    public static SharedPreferences getSP(Context context, String name){
        return ContextUtils.getApplication(context).getSharedPreferences(name,Context.MODE_PRIVATE);
    }

    public static SharedPreferences getSP(Context context, String name, int mode){
        return ContextUtils.getApplication(context).getSharedPreferences(name,mode);
    }

    public static SharedPreferences.Editor getEditor(Context context, String name){
        return getSP(context,name).edit();
    }

    public static SharedPreferences.Editor getEditor(Context context, String name, int mode){
        return getSP(context,name,mode).edit();
    }

}
