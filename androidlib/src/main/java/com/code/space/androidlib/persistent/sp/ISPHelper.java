package com.code.space.androidlib.persistent.sp;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by shangxuebin on 16-5-27.
 */
public interface ISPHelper {
    SharedPreferences getSP(String fileName, Context openSp);
    SharedPreferences getSP(String fileName, int mode, Context openSp);
    SharedPreferences.Editor getEditor(String fileName, Context openSp);
    SharedPreferences.Editor getEditor(String fileName, int mode, Context openSp);
}
