package com.code.space.androidlib.net.toolbox;

import com.code.space.androidlib.utilities.CommonException;
import com.code.space.androidlib.utilities.chain.AbstractChainBuilder;
import com.code.space.androidlib.utilities.chain.string.IStringBuilder;
import com.code.space.androidlib.utilities.utils.AppUtils;
import com.code.space.androidlib.utilities.utils.LogUtils;
import com.code.space.androidlib.utilities.utils.ObjectUtils;
import com.code.space.androidlib.utilities.utils.StringUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by shangxuebin on 16-4-7.
 */
public class ParamsBuilder extends AbstractChainBuilder<Map<String,String>> implements IStringBuilder<Map<String,String>>{

    public ParamsBuilder() {
        setTarget(new HashMap<String, String>());
    }

    public String toGet() {
        return StringUtils.map2GetParams(getTarget());
    }

    public String toGet(String root) {
        StringBuilder sb = new StringBuilder(root).append("?");
        StringUtils.map2GetParams(sb,getTarget());
        return sb.toString();
    }

    public JSONObject toJson() {
        try {
            JSONObject jObj = new JSONObject();
            Iterator<Map.Entry<String, String>> it = getTarget().entrySet().iterator();
            for(Map.Entry<String,String> entry:getTarget().entrySet()){
                jObj.put(entry.getKey(), entry.getValue());
            }
            return jObj;
        } catch (Exception e) {
            if(AppUtils.debugging()) throw new CommonException(e);
            return null;
        }
    }

    public ParamsBuilder putJson(JSONObject jsonObject){
        Iterator<String> keys = jsonObject.keys();
        String key;
        while(keys.hasNext()){
            key = keys.next();
            getTarget().put(key,ObjectUtils.toString(jsonObject.opt(key)));
        }
        return this;
    }


    public ParamsBuilder put(String key, Object value) {
        put(key, ObjectUtils.toString(value));
        return this;
    }

    public ParamsBuilder put(String key, String value){
        getTarget().put(key, StringUtils.toString(value));
        return this;
    }

    @Override
    public IStringBuilder<Map<String, String>> put(String key, byte value) {
        return put(key,String.valueOf(value));
    }

    @Override
    public IStringBuilder<Map<String, String>> put(String key, char value) {
        return put(key,String.valueOf(value));
    }

    @Override
    public IStringBuilder<Map<String, String>> put(String key, short value) {
        return put(key,String.valueOf(value));
    }

    @Override
    public IStringBuilder<Map<String, String>> put(String key, int value) {
        return put(key,String.valueOf(value));
    }

    @Override
    public IStringBuilder<Map<String, String>> put(String key, long value) {
        return put(key,String.valueOf(value));
    }

    @Override
    public IStringBuilder<Map<String, String>> put(String key, float value) {
        return put(key,String.valueOf(value));
    }

    @Override
    public IStringBuilder<Map<String, String>> put(String key, double value) {
        return put(key,String.valueOf(value));
    }

    @Override
    public IStringBuilder<Map<String, String>> put(String key, boolean value) {
        return put(key,String.valueOf(value));
    }
}
