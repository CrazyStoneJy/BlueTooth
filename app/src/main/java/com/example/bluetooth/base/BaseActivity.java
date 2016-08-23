package com.example.bluetooth.base;

import android.content.Intent;
import android.os.Bundle;

import com.code.space.androidlib.context.activity.CSBaseActivity;
import com.code.space.javalib.viewinject.ViewBinder;

/**
 * Created by Administrator on 2016/8/23.
 */
public class BaseActivity extends CSBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewBinder.bind(this);
        init(getIntent());
    }

    protected void init(Intent intent) {

    }
}
