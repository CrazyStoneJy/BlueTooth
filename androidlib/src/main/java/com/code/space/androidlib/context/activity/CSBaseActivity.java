package com.code.space.androidlib.context.activity;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.code.space.androidlib.annotation.ActivityExtra;
import com.code.space.androidlib.annotation.ContentView;
import com.code.space.androidlib.context.IBaseUI;
import com.code.space.androidlib.context.LifeCircle;
import com.code.space.androidlib.utilities.utils.AppUtils;
import com.code.space.androidlib.utilities.utils.CollectionUtils;
import com.code.space.androidlib.utilities.utils.LogUtils;
import com.code.space.androidlib.utilities.utils.UIUtils;
import com.code.space.androidlib.utilities.utils.ViewUtils;
import com.code.space.androidlib.view.finder.WrongViewTypeException;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;

/**
 * basic activity class for all activity
 */
public class CSBaseActivity extends AppCompatActivity implements IBaseUI,LifeCircle {

    /**
     * 保存当前
     */
    public static WeakReference<Activity> instance;

    protected boolean addToStack, hideKeyboard;

    protected View.OnTouchListener touchListener;
    protected List<View.OnTouchListener> touchListenerList;

    protected boolean callOnTouch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onLifeCircle(CREATE);
        Class<? extends Activity> clazz = getClass();
        if (clazz.isAnnotationPresent(ContentView.class)) {
            setContentView(clazz.getAnnotation(ContentView.class).value());
        }
        if (clazz.isAnnotationPresent(ActivityExtra.class)) {
            ActivityExtra extra = clazz.getAnnotation(ActivityExtra.class);
            if (extra.storeInstance()) instance = new WeakReference<Activity>(this);
            addToStack = extra.addToStack();
            setAutoHideKeyboard(extra.hideKeyboard());
        }
        if (addToStack) ActivityStack.put(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(AppUtils.debugging())LogUtils.log(getClass().getSimpleName(),"onStart");
        onLifeCircle(START);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(AppUtils.debugging())LogUtils.log(getClass().getSimpleName(),"onRestart");
        onLifeCircle(RESTART);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(AppUtils.debugging())LogUtils.log(getClass().getSimpleName(),"onResume");
        onLifeCircle(RESUME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(AppUtils.debugging())LogUtils.log(getClass().getSimpleName(),"onPause");
        onLifeCircle(PAUSE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(AppUtils.debugging())LogUtils.log(getClass().getSimpleName(),"onStop");
        onLifeCircle(STOP);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(AppUtils.debugging())LogUtils.log(getClass().getSimpleName(),"onDestroy");
        if (addToStack) ActivityStack.pop();
        onLifeCircle(DESTROY);
        instance = null;
    }

    protected void onLifeCircle(int life) {
    }

    protected <VIEW extends View> VIEW $(@IdRes int id) {
        return (VIEW) findViewById(id);
    }

    protected void $(View.OnClickListener l, Object... views) {
        for (Object v : views) {
            if (v instanceof Integer) findViewById((Integer) v).setOnClickListener(l);
            else if (v instanceof View) ((View) v).setOnClickListener(l);
            else if(AppUtils.debugging())throw new WrongViewTypeException(v);
        }
    }

    public void setAutoHideKeyboard(boolean auto){
        this.hideKeyboard = auto;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (hideKeyboard) {//需要隐藏键盘
            if (event.getAction() == MotionEvent.ACTION_UP) {//手指抬起
                View view = getCurrentFocus();
                if(view!=null) {
                    if (view instanceof EditText) {//焦点在EditText里
                        if (!ViewUtils.isInViewRange(event, view, 20)) {//在EditText周围20像素内
                            UIUtils.hideKeyboard((EditText) view);//隐藏键盘
                        }
                    }
                }
            }
        }
        if(callOnTouch){
            View view = getCurrentFocus();
            if(touchListener!=null) {
                if(touchListener.onTouch(view,event)) return true;
            }
            if(CollectionUtils.notEmpty(touchListenerList)){
                for(View.OnTouchListener listener:touchListenerList){
                    if(listener.onTouch(view,event)) return true;
                }
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public CSBaseActivity getMyActivity() {
        return this;
    }

    @Override
    public FragmentManager getFragmentManagerForPresenter() {
        return getSupportFragmentManager();
    }

    @Override
    public int fragmentHolderId(int index) {
        return -1;
    }

    public void setCallOnTouch(boolean call){
        this.callOnTouch = call;
    }

    public void setOnTouchListener(View.OnTouchListener listener){
        this.touchListener = listener;
    }

    public void addOnTouchListener(View.OnTouchListener listener){
        if(touchListenerList==null) touchListenerList = new LinkedList<View.OnTouchListener>();
        touchListenerList.add(listener);
    }

    public void removeOnTouchListener(View.OnTouchListener listener){
        if(touchListenerList==null) return;
        touchListenerList.remove(listener);
    }

}
