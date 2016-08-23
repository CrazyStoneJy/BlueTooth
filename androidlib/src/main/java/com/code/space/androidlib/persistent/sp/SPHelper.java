package com.code.space.androidlib.persistent.sp;

import android.content.Context;
import android.content.SharedPreferences;

import com.code.space.androidlib.utilities.utils.ContextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shangxuebin on 16-5-27.
 */
public class SPHelper implements ISPHelper {

    private Map<String, SharedPreferences> spMap = new HashMap<>();

    @Override
    public SharedPreferences getSP(String fileName, Context openSp) {
        return getSP(fileName, Context.MODE_PRIVATE, openSp);
    }

    @Override
    public SharedPreferences getSP(String fileName, int mode, Context openSp) {
        SharedPreferences sp = spMap.get(fileName);
        if (sp == null) {
            sp = ContextUtils.getApplication(openSp).getSharedPreferences(fileName, mode);
            spMap.put(fileName, sp);
        }
        return sp;
    }

    @Override
    public SharedPreferences.Editor getEditor(String fileName, Context openSp) {
        return getEditor(fileName, Context.MODE_PRIVATE, openSp);
    }

    @Override
    public SharedPreferences.Editor getEditor(String fileName, int mode, Context openSp) {
        return getSP(fileName, mode, openSp).edit();
    }
}
