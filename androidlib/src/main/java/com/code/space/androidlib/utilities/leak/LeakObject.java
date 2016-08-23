package com.code.space.androidlib.utilities.leak;

import com.code.space.androidlib.utilities.ICheckLegal;
import com.code.space.androidlib.utilities.utils.ObjectUtils;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by shangxuebin on 16-6-18.
 */
public class LeakObject<O> implements ICheckLegal{

    protected Reference<O> leakTarget;

    public LeakObject() {
    }

    public LeakObject(O target) {
        this.leakTarget = new WeakReference<O>(target);
    }

    public O getTarget() {
        return ObjectUtils.get(leakTarget);
    }

    public void setTarget(O target) {
        this.leakTarget = new WeakReference<O>(target);
    }

    @Override
    public boolean checkLegal() {
        return ObjectUtils.get(leakTarget)!=null;
    }
}
