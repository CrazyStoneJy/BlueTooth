package com.code.space.androidlib.utilities.leak;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by shangxuebin on 2016/5/17.
 */
public abstract class LeakHandler<OUT> extends Handler {

    protected WeakReference<OUT> outer;

    public LeakHandler(OUT out) {
        setOuter(out);
    }

    public void setOuter(OUT out) {
        this.outer = new WeakReference<OUT>(out);
    }

    @Override
    public void handleMessage(Message msg) {
        OUT o = outer.get();
        if(o==null){
            removeCallbacksAndMessages(null);
        }else {
            handleMessage(o,msg);
        }
    }

    public abstract void handleMessage(OUT out, Message msg);
}
