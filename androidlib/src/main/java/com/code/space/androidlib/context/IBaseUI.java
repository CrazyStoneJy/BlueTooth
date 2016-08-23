package com.code.space.androidlib.context;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentManager;

import com.code.space.androidlib.context.activity.CSBaseActivity;

/**
 * Created by shangxuebin on 16-5-16.
 */
public interface IBaseUI {
    Activity getMyActivity();
    FragmentManager getFragmentManagerForPresenter();
    int fragmentHolderId(int index);
}
