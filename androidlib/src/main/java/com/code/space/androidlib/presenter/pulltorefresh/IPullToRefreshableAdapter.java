package com.code.space.androidlib.presenter.pulltorefresh;


import java.util.List;

/**
 * Created by shangxuebin on 16-5-17.
 */
public interface IPullToRefreshableAdapter<E> {
    void refreshView();
    void append(List<E> addList);
    void flush(List<E> changeAll);
}
