package com.code.space.androidlib.presenter.fragment;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.code.space.androidlib.presenter.BasePresenter;
import com.code.space.androidlib.context.IBaseUI;
import com.code.space.androidlib.utilities.utils.AppUtils;

/**
 * Created by shangxuebin on 16-4-8.
 */
public abstract class FragmentPresenter<UI extends IBaseUI> extends BasePresenter<UI> implements IFragmentPresenter {

    protected Fragment[] fragments;

    protected int lastIndex = -1;

    private boolean replace;

    public FragmentPresenter(UI ui) {
        super(ui);
    }

    public FragmentPresenter(UI ui, int count) {
        super(ui);
        fragments = new Fragment[count];
    }

    public void setReplace(){
        replace = true;
    }

    @Override
    public void changeFragment(int index) {
        if (index == lastIndex) return;
        if (index >= fragments.length)
            if (AppUtils.debugging()){
                throw new FragmentIndexOutOfBoundsException(index, fragments.length);
            }else {
                return;
            }
        FragmentTransaction transaction = mUI.getFragmentManagerForPresenter().beginTransaction();
        Fragment f;
        if(replace){
            f = fragments[index];
            if(f==null){
                f=createFragment(index);
                fragments[index] = f;
            }
            transaction.replace(mUI.fragmentHolderId(-1), f);
        }else {
            if (lastIndex >= 0) {
                f = fragments[lastIndex];
                if (f != null && f.isAdded()) transaction.hide(f);
            }
            f = fragments[index];
            if (f == null) {
                f = createFragment(index);
                fragments[index] = f;
            }
            if (f.isAdded()) transaction.show(f);
            else transaction.add(mUI.fragmentHolderId(-1), f);
        }
        transaction.commit();
        lastIndex = index;
    }

    protected abstract Fragment createFragment(int position);

    @Override
    @SuppressWarnings("unchecked")
    public <F extends Fragment> F getFragment(int index) {
        return (F) fragments[index];
    }
}
