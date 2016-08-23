package com.code.space.androidlib.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.code.space.androidlib.presenter.pulltorefresh.IPullToRefreshableAdapter;

import java.util.List;

/**
 * Generic Adapter for RecyclerView
 */
public class GenericAdapter<ITEM,VH extends GenericViewHolder<ITEM>>
        extends RecyclerView.Adapter<VH> implements IPullToRefreshableAdapter<ITEM> {

    private List<ITEM> data;
    private OnItemClickListener itemListener;

    public static final int POSITION_FLAT_FIRST = 1;
    public static final int POSITION_FLAT_NORMAL = POSITION_FLAT_FIRST + 1;
    public static final int POSITION_FLAT_LAST = POSITION_FLAT_NORMAL + 1;

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }


    @Override
    public void onBindViewHolder(VH holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }

    @Override
    public void refreshView() {
        notifyDataSetChanged();
    }

    @Override
    public void append(List addList) {
        if(data!=null)data.addAll(addList);
        else data = addList;
    }

    @Override
    public void flush(List changeAll) {
        if(data!=null)data.clear();
        if(changeAll!=null)append(changeAll);
    }

    public ITEM getItem(int position) {
        if(checkPositionIllegal(position)) return null;
        return data.get(position);
    }

    void onItemClick(int position) {
        if(checkPositionIllegal(position)) return;
        if (itemListener != null) itemListener.onItemClick(position,this);
    }

    private boolean checkPositionIllegal(int position){
        if(data==null) return true;
        return position<0 || position>=getItemCount();
    }

    public interface OnItemClickListener{
        void onItemClick(int position, GenericAdapter adapter);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.itemListener = listener;
    }
}
