package com.code.space.androidlib.utilities.utils;

import android.text.TextUtils;

import com.code.space.androidlib.utilities.reflect.GenericGetter;

import java.lang.ref.Reference;
import java.lang.reflect.Constructor;

public class ObjectUtils {

    public static boolean isNull(Object... objects) {
        for (Object o : objects) {
            if (o == null) return true;
        }
        return false;
    }

    public static boolean notNull(Object... objects) {
        return !isNull(objects);
    }

    public static String toString(Object o) {
        return toString(o, StringUtils.EMPTY);
    }

    public static String toString(String s) {
        return toString(s, StringUtils.EMPTY);
    }

    public static String toString(String s, String replace) {
        return TextUtils.isEmpty(s) ? replace : s;
    }

    public static String toString(Object o, String replace) {
        return o == null ? replace : o.toString();
    }

    public static <T> void forEach(ForEachCallBack<T> callBack, T... objects) {
        if (CollectionUtils.isEmpty(objects)) return;
        int length = objects.length;
        for (int i = 0; i < length; i++) {
            callBack.doSomething(objects[i], i, length);
        }
    }

    public interface ForEachCallBack<T> {
        void doSomething(T t, int index, int totalLength);
    }

    public static int getInIntArray(int index, int... objects) {
        if (objects == null) {
            if (AppUtils.debugging()) throw new NullPointerException("Nothing to get");
            else return -1;
        }
        if (index >= objects.length) {
            if (AppUtils.debugging())
                throw new ArrayIndexOutOfBoundsException("the index +" + index + " you want to pick is larger than the length of array " + objects.length);
            else return -1;
        }
        if (index < 0) {
            if (AppUtils.debugging())
                throw new ArrayIndexOutOfBoundsException("the index +" + index + " you want to pick is less than zero");
            else return -1;
        }
        return objects[index];
    }

    public static <T>T getInTypeArray(int index, T... objects){
        if (objects == null) {
            if (AppUtils.debugging()) throw new NullPointerException("Nothing to get");
            else return null;
        }
        if (index >= objects.length) {
            if (AppUtils.debugging())
                throw new ArrayIndexOutOfBoundsException("the index +" + index + " you want to pick is larger than the length of array " + objects.length);
            else return null;
        }
        if (index < 0) {
            if (AppUtils.debugging())
                throw new ArrayIndexOutOfBoundsException("the index +" + index + " you want to pick is less than zero");
            else return null;
        }
        return objects[index];
    }

    @SuppressWarnings("unchecked")
    public static <T>T getInMap(Object key, Object... keyValues){
        int length = keyValues.length;
        if(length%2!=0){
            if(AppUtils.debugging()) throw new IllegalArgumentException("the number of key and values is not even");
            else length--;
        }
        if(length<=0) return null;
        for(int i=0; i<length; i+=2){
            if(key==null){
                if(keyValues[i]==null) return (T)keyValues[i+1];
            }else {
                if(key.equals(keyValues[i])) return (T)keyValues[i+1];
            }
        }
        return null;
    }

    public static <T> T checkNull(T t) {
        if (t != null) return t;
        if (AppUtils.debugging()) throw new NullPointerException("Null object");
        else {
            try {
                Class<T> clazz = new GenericGetter<T>(){}.getGenericClass();
                Constructor<T> c = clazz.getConstructor();
                return c.newInstance();
            } catch (Exception e) {
                LogUtils.ex(e);
            }
        }
        return null;
    }

    public static <T>T get(Reference<T> reference){
        return reference==null?null:reference.get();
    }

    public static boolean equals(Object a, Object b){
        return equals(a,b,false);
    }

    public static boolean equals(Object a, Object b, boolean allowNull){
        if(a==null){
            if(allowNull)return b==null;
            return false;
        }
        if(a==b) return true;
        return a.equals(b);
    }
}
