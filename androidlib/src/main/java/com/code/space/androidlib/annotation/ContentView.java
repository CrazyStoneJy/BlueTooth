package com.code.space.androidlib.annotation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.code.space.androidlib.context.activity.CSBaseActivity;
import com.code.space.androidlib.context.fragment.CSBaseFragment;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by shangxuebin on 16-4-7.
 * set the content view for activity or fragment
 * @see CSBaseActivity#onCreate(Bundle)
 * @see CSBaseFragment#onCreateView(LayoutInflater, ViewGroup, Bundle)
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ContentView {
    /**
     * the layout id
     * @return a valid positive integer value of a layout resource id
     */
    int value();
}
