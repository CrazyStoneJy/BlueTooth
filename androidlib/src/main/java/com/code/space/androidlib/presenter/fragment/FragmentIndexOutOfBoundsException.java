package com.code.space.androidlib.presenter.fragment;

/**
 * Created by shangxuebin on 16-4-8.
 */
public class FragmentIndexOutOfBoundsException extends IndexOutOfBoundsException{
    public FragmentIndexOutOfBoundsException(int index, int total) {
        super("The fragment index: "+index+" is larger than total fragments' count: "+total);
    }
}
