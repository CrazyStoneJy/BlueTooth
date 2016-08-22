package com.example.bluetooth.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bluetooth.R;
import com.example.bluetooth.utils.DeviceModel;

import java.util.List;

/**
 * Created by Administrator on 2016/8/22.
 */
public class BlueToothAdapter extends RecyclerView.Adapter<BlueToothAdapter.BlueToothViewHolder> {

    private List<DeviceModel> mList;
    private Context mContext;

    public BlueToothAdapter(Context context, List<DeviceModel> list) {
        this.mList = list;
        this.mContext = context;
    }


    @Override
    public BlueToothViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_device, null);
        return new BlueToothViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BlueToothViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    class BlueToothViewHolder extends RecyclerView.ViewHolder {

        public BlueToothViewHolder(View itemView) {
            super(itemView);
        }
    }
}
