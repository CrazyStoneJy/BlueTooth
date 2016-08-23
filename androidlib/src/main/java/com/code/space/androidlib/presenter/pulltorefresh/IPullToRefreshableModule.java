package com.code.space.androidlib.presenter.pulltorefresh;

import java.util.List;

/**
 * Created by shangxuebin on 16-5-17.
 */
public interface IPullToRefreshableModule<E> {
    List<E> getList();
    boolean notMoreData();
}
