package com.code.space.androidlib.utilities.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import java.lang.ref.WeakReference;

/**
 * Created by shangxuebin on 16-4-7.
 */
public class UIUtils {

    private static WeakReference<Dialog> mDialog;

    public static void showDialog(Dialog dialog){
        if(dialog==null) return;
        dismissDialog();
        mDialog = new WeakReference<Dialog>(dialog);
        if(!dialog.isShowing())dialog.show();
    }

    public static void dismissDialog(){
        if(dialogShowing()) mDialog.get().dismiss();
    }

    public static boolean dialogShowing(){
        return mDialog!=null && mDialog.get()!=null && mDialog.get().isShowing();
    }


    /**
     * hide a in-screen keyboard
     *
     * @param et the EditText object to get context and the window token
     */
    public static void hideKeyboard(EditText et) {
        InputMethodManager imm = (InputMethodManager) et.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
        }
    }


    public static void showKeyboard(EditText et) {
        InputMethodManager imm = (InputMethodManager) et.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.showSoftInputFromInputMethod(et.getWindowToken(), 0);
        }
    }
}
