package com.code.space.androidlib.secure;

import com.code.space.androidlib.utilities.utils.AppUtils;
import com.code.space.androidlib.utilities.utils.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by shangxuebin on 16-8-22.
 */
public class StringEncoder {

    public static String sha1(String target){
        return StringEncoder.encode(target,"SHA-1");
    }

    public static String encode(String target , String method){
        MessageDigest md = null;
        try {
             md = MessageDigest.getInstance(method);
        }catch (NoSuchAlgorithmException e){
            if(AppUtils.debugging()) throw new RuntimeException(e);
        }
        if(md==null) return StringUtils.EMPTY;
        md.update(target.getBytes());
        return convertToHex(md.digest());
    }

    private static String convertToHex(byte[] data) {
        int dataLength = data.length;
        if(dataLength<=0) return StringUtils.EMPTY;
        int resultLength = dataLength<<1;
        if(resultLength<0||resultLength>512) resultLength = 512;
        char[] result = new char[resultLength];
        //byte高4位
        byte temp;
        //byte低4位
        for(int d=0,r=0; r<resultLength; d=(d>=dataLength?0:d+1),r+=2){
            temp = data[d];
            result[r] = intToHex(temp>>>4);
            result[r+1]=intToHex(temp);
        }
        return new String(result);
    }

    public static char intToHex(int i){
        i &= 0x0f;
        return (char)(i<10?(48+i):(97+i-10));
    }
}
