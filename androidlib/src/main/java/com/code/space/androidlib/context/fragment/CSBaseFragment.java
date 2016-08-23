package com.code.space.androidlib.context.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.code.space.androidlib.annotation.ContentView;
import com.code.space.androidlib.context.IBaseUI;
import com.code.space.androidlib.context.LifeCallback;
import com.code.space.androidlib.context.LifeCircle;
import com.code.space.androidlib.view.finder.WrongViewTypeException;

import static com.code.space.androidlib.context.LifeCircle.*;

/**
 * Created by shangxuebin on 16-4-7.
 */
public class CSBaseFragment extends Fragment implements
        IBaseUI,LifeCallback{

    protected View mView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onLifeCircle(CREATE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onLifeCircle(DESTROY);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(getClass().isAnnotationPresent(ContentView.class)){
            mView = inflater.inflate(getClass().getAnnotation(ContentView.class).value(),container,false);
        }
        onLifeCircle(CREATE_VIEW);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onLifeCircle(VIEW_CREATED);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onLifeCircle(ATTACH);
    }

    @Override
    public void onStart() {
        super.onStart();
        onLifeCircle(START);
    }

    @Override
    public void onPause() {
        super.onPause();
        onLifeCircle(PAUSE);
    }

    @Override
    public void onResume() {
        super.onResume();
        onLifeCircle(RESUME);
    }

    @Override
    public void onStop() {
        super.onStop();
        onLifeCircle(STOP);
    }

    @SuppressWarnings("unchecked")
    protected <VIEW extends View>VIEW $(@IdRes int id){
        return (VIEW)mView.findViewById(id);
    }

    protected void $(View.OnClickListener l, Object... views){
        for(Object v:views){
            if(v instanceof Integer) mView.findViewById((Integer)v).setOnClickListener(l);
            else if(v instanceof View) ((View)v).setOnClickListener(l);
            else throw new WrongViewTypeException(v);
        }
    }

    protected void onItemClick2(int position){}

    @Override
    public FragmentManager getFragmentManagerForPresenter() {
        return getChildFragmentManager();
    }

    @SuppressWarnings("unchecked")
    public Activity getMyActivity(){
        return getActivity();
    }

    @Override
    public int fragmentHolderId(int index) {
        return 0;
    }

    public void onLifeCircle(int life) {
    }

}
