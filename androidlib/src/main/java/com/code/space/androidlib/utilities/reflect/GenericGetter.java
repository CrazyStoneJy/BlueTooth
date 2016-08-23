package com.code.space.androidlib.utilities.reflect;

import com.code.space.androidlib.utilities.utils.AppUtils;

import java.lang.reflect.ParameterizedType;

/**
 * Created by shangxuebin on 16-5-24.
 */
public class GenericGetter<T> {

    @SuppressWarnings("unchecked")
    public Class<T> getGenericClass(){
        System.out.println("getClass="+getClass().getName());
        if(AppUtils.debugging())if(getClass().equals(GenericGetter.class)) throw new WrongGenericTypeException();
        try {
            ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
            return (Class<T>) (type.getActualTypeArguments()[0]);
        }catch (Exception e){
            if(AppUtils.debugging()){
                WrongGenericTypeException wtException = new WrongGenericTypeException();
                wtException.initCause(e);
                throw wtException;
            }
            else return null;
        }
    }
}
