package com.code.space.androidlib.view.finder;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.view.View;

import com.code.space.androidlib.utilities.CommonException;
import com.code.space.androidlib.utilities.utils.AppUtils;

public class ViewFinder {

    private IFindViewAble findView;

    public ViewFinder(final View view) {
        this(new IFindViewAble() {
            @Override
            public View findViewById(@IdRes int id) {
                return view.findViewById(id);
            }
        });
    }

    public ViewFinder(final Activity activity) {
        this(new IFindViewAble() {
            @Override
            public View findViewById(@IdRes int id) {
                return activity.findViewById(id);
            }
        });
    }

    public ViewFinder(IFindViewAble findView) {
        this.findView = findView;
    }

    public void setFindView(IFindViewAble f){
        this.findView = f;
    }

    public void recycle() {
        findView = null;
    }

    @SuppressWarnings("unchecked")
    public <VIEW extends View> VIEW fv(@IdRes int id) {
        if (findView == null) {
            if (AppUtils.debugging())
                throw new CommonException("Do not use view finder after recycled");
            else return null;
        }
        return (VIEW) findView.findViewById(id);
    }

    public void setOnClickListener(View.OnClickListener l, Object... views) {
        for (Object v : views) {
            if (v instanceof Integer) fv((Integer) v).setOnClickListener(l);
            else if (v instanceof View) ((View) v).setOnClickListener(l);
            else{
                if(AppUtils.debugging())throw new WrongViewTypeException(v);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <VIEW extends View> VIEW findView(@IdRes int id, View parentView) {
        return (VIEW)parentView.findViewById(id);
    }

    @SuppressWarnings("unchecked")
    public static <VIEW extends View> VIEW findView(@IdRes int id, Activity parentActivity) {
        return (VIEW)parentActivity.findViewById(id);
    }


}
