package com.code.space.androidlib.presenter.fragment;

import android.support.v4.app.Fragment;

/**
 * Created by shangxuebin on 16-4-11.
 */
public interface IFragmentPresenter {
    void changeFragment(int index);
    <F extends Fragment>F getFragment(int index);
}
