package com.code.space.androidlib.presenter.pulltorefresh;

import com.code.space.androidlib.context.IBaseUI;

/**
 * Created by shangxuebin on 16-5-19.
 */
public interface IPullToRefreshableUI extends IBaseUI{
    void netFinish(boolean success);
    void showLastPage();
    void showNoData();
}
