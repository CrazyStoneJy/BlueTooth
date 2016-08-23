package com.code.space.androidlib.utilities.utils;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by shangxuebin on 16-5-16.
 */
public class StringUtils {

    public static final String EMPTY = "";
    public static final String ZERO = "0";
    public static final String ZERO_DECIMAL = "0.00";

    public static String toString(String s){
        return toString(s,EMPTY);
    }

    public static String toString(String s,String replace){
        return s==null?replace:s;
    }

    public static String map2GetParams(String url, Map<?,?> params){
        if(CollectionUtils.isEmpty(params)) return url;
        StringBuilder sb = new StringBuilder(url).append("?");
        map2GetParams(sb,params);
        return sb.toString();
    }

    public static String map2GetParams(Map<?,?> keyValue){
        StringBuilder sb = new StringBuilder();
        map2GetParams(sb,keyValue);
        return sb.toString();
    }

    public static void map2GetParams(StringBuilder sb, Map<?,?> params){
        if(CollectionUtils.isEmpty(params) || sb==null){
            //if(AppUtils.debugging()) throw new NullPointerException("Your "+(sb==null?"StringBuilder":"params")+" is null or empty");
            return;
        }
        Iterator<? extends Map.Entry<?, ?>> entryIt = params.entrySet().iterator();
        Map.Entry<?,?> entry;
        int i=0;
        while(entryIt.hasNext()) {
            if (i > 0) sb.append("&");
            entry = entryIt.next();
            if(TextUtils.isEmpty(ObjectUtils.toString(entry.getKey(),null))) continue;
            sb.append(entry.getKey())
                    .append("=")
                    .append(ObjectUtils.toString(entry.getValue()));
            i++;
        }
    }


    public static <ENTITY>ENTITY json2Entity(String json, Class<ENTITY> clazz){
        return JSON.parseObject(json,clazz);
    }

    public static <ENTITY>ENTITY json2Entity(String json){
        return JSON.parseObject(json,new TypeReference<ENTITY>(){});
    }

    public static String entity2Json(Object entity){
        return JSON.toJSONString(entity);
    }

    public static <ENTITY>ENTITY xml2Entity(String xml){
        return null;
    }

    public static <ENTITY>ENTITY xml2Entity(String xml, Class<ENTITY> clazz){
        return null;
    }
    public static String entity2Xml(Object entity){
        return null;
    }


    public static boolean notEmpty(CharSequence charSequence){
        return !TextUtils.isEmpty(charSequence);
    }


    public static String splice(Object... os){
        StringBuilder sb = new StringBuilder();
        for(Object o:os){
            sb.append(ObjectUtils.toString(o));
        }
        return sb.toString();
    }
}
