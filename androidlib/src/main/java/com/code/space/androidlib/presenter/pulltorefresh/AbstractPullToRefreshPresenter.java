package com.code.space.androidlib.presenter.pulltorefresh;

import android.support.v4.widget.SwipeRefreshLayout;
import com.code.space.androidlib.net.ResponseListener;
import com.code.space.androidlib.presenter.BasePresenter;
import com.code.space.androidlib.utilities.utils.UIUtils;

import static com.code.space.androidlib.presenter.pulltorefresh.IPullToRefreshPresenter.PULL_DOWN;
import static com.code.space.androidlib.presenter.pulltorefresh.IPullToRefreshPresenter.PULL_UP;

/**
 * Created by shangxuebin on 16-5-17.
 */
public abstract class AbstractPullToRefreshPresenter<E, LIST_MODULE extends IPullToRefreshableModule<E>> extends BasePresenter<IPullToRefreshableUI>
        implements ResponseListener<LIST_MODULE>,SwipeRefreshLayout.OnRefreshListener{

    protected IPullToRefreshableAdapter<E> mAdapter;

    protected int lastIndex = 0;
    protected int listCount = 10;

    protected String url,startParam,countParam;

    public AbstractPullToRefreshPresenter(IPullToRefreshableUI ui) {
        super(ui);
    }

    public void initialization(){
        mAdapter = getAdapter();
        getNetData(PULL_DOWN);
    }

    protected void getNetData(int mode){
        switch (mode){
            case PULL_DOWN:
                lastIndex = 0;
                break;
            case PULL_UP:
                lastIndex += listCount;
                break;
        }
        getNetData(lastIndex,listCount);
    }

    protected abstract void getNetData(int lastIndex, int listCount);

    protected abstract IPullToRefreshableAdapter<E> getAdapter();

//    @Override
//    public void onNetResult(boolean success, LIST_MODULE module, int id) {
//        netFinish(success);
//        if(success){
//            switch (id){
//                case PULL_DOWN:
//                    if(module.notMoreData()) mUI.showLastPage();
//                    else mAdapter.flush(module.getList());
//                    break;
//                case PULL_UP:
//                    if(module.notMoreData()) mUI.showNoData();
//                    else mAdapter.append(module.getList());
//                    break;
//            }
//            mAdapter.refreshView();
//        }
//    }

    protected void netFinish(boolean success){
        UIUtils.dismissDialog();
        mUI.netFinish(success);
    }
}
