package com.code.space.androidlib.utilities.utils;

import com.code.space.androidlib.utilities.reflect.GenericGetter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by shangxuebin on 16-4-7.
 */
public class CollectionUtils {

    public static boolean isEmpty(Collection collection){
        return collection==null||collection.isEmpty();
    }
    public static boolean notEmpty(Collection collection){
        return !isEmpty(collection);
    }

    public static boolean notEmpty(Collection... collections) {
        return !isEmpty(collections);
    }

    public static boolean isEmpty(Collection... collections) {
        if(collections==null) return false;
        if(collections.length==1) return notEmpty(collections[0]);
        if(collections.length==2) return notEmpty(collections[0]) && notEmpty(collections[1]);
        for (Collection collection : collections) {
            if (collection==null||collection.isEmpty()) return true;
        }
        return false;
    }

    public static boolean isEmpty(Map map){
        return map==null||map.isEmpty();
    }
    public static boolean notEmpty(Map map){
        return !isEmpty(map);
    }

    public static boolean isEmpty(Dictionary<?,?> dictionary){
        return dictionary==null||dictionary.isEmpty();
    }
    public static boolean notEmpty(Dictionary<?,?> dictionary){
        return !isEmpty(dictionary);
    }

    public static <A>boolean isInArray(A[] array, A a){
        if(isEmpty(array)) return false;
        for(A check:array){
            if(a == check) return true;
        }
        return false;
    }

    public static <A>boolean isEmpty(A[] array){
        return array==null||array.length==0;
    }

    public static <A>boolean notEmpty(A[] array){
        return !isEmpty(array);
    }

    public static boolean isEmpty(byte[] array){
        return array==null||array.length==0;
    }

    public static boolean notEmpty(byte[] array){
        return !isEmpty(array);
    }

    public static int size(Collection c){
        return c==null?0:c.size();
    }

    public static int size(Map<?,?> map){
        return map==null?0:map.size();
    }

    public static <E>int size(E[] e){
        return e==null?0:e.length;
    }

    public static int size(int[] e){
        return e==null?0:e.length;
    }

    @SuppressWarnings("unchecked")
    public static <E>E[] toOneArray(E[]... eArrays){
        int capacity = 0;
        if(isEmpty(eArrays))return (E[]) Array.newInstance(new GenericGetter<E>(){}.getGenericClass(),0);
        for(int i=0; i<eArrays.length; i++){
            capacity += size(eArrays[i]);
        }
        if(capacity==0) return (E[]) Array.newInstance(new GenericGetter<E>(){}.getGenericClass(),0);
        E[] result = (E[]) Array.newInstance(new GenericGetter<E>(){}.getGenericClass(),capacity);
        int index = 0;
        for(int i=0; i<eArrays.length; i++){
            for(int j=0; j<size(eArrays[i]); j++){
                result[index++] = eArrays[i][j];
            }
        }
        return result;
    }

    public static int[] toOneArray(int[]... arrays){
        if(isEmpty(arrays)) return new int[0];
        int capacity = 0;
        for(int i=0; i<arrays.length; i++){
            capacity+=size(arrays[i]);
        }
        int[] result = new int[capacity];
        if(capacity==0) return result;
        for(int i=0,index=0; i<arrays.length; i++){
            for(int j=0; j<size(arrays[i]); j++){
                result[index++] = arrays[i][j];
            }
        }
        return result;
    }

    public static <E>List<E> asArrayList(E... elements){
        if(isEmpty(elements)) return new ArrayList<E>();
        List<E> list = new ArrayList<E>(size(elements));
        for(E e:elements){
            list.add(e);
        }
        return list;
    }

    public static <E>List<E> addAll(List<E> list,E... elements){
        if(CollectionUtils.isEmpty(elements)) return list;
        if(list==null) return list = new ArrayList<E>(elements.length);
        for(E e:elements){
            list.add(e);
        }
        return list;
    }

}
