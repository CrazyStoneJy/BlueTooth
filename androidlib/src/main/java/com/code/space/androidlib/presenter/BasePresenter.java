package com.code.space.androidlib.presenter;

import android.app.Activity;
import android.content.Intent;

import com.code.space.androidlib.context.IBaseUI;
import com.code.space.androidlib.context.activity.CSBaseActivity;
import com.code.space.androidlib.utilities.utils.LogUtils;

/**
 * Created by shangxuebin on 16-4-7.
 */
public abstract class BasePresenter<UI extends IBaseUI>{

    protected UI mUI;

    public BasePresenter(UI ui) {
        LogUtils.logJavaFile("presenter",this);
        this.mUI = ui;
    }

    protected Activity getActivity(){
        return mUI.getMyActivity();
    }

    protected void startActivity(Intent i){
        getActivity().startActivity(i);
    }

    protected void startActivity(Class<? extends Activity> clazz){
        startActivity(new Intent(getActivity(),clazz));
    }



}
