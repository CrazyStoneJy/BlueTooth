package com.code.space.androidlib.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by shangxuebin on 16-5-19.
 */
public class GenericViewHolder<ITEM> extends RecyclerView.ViewHolder{

    int position = -1;
    private GenericAdapter adapter;
    private View.OnClickListener onClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            if(adapter!=null) adapter.onItemClick(position);
        }
    };

    public GenericViewHolder(View itemView, GenericAdapter a){
        super(itemView);
        this.adapter = a;
        itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                adapter.onItemClick(position);
            }
        });
    }

    public void bindView(int position, ITEM item, int positionFlag, int typeFlag){

    }
}
