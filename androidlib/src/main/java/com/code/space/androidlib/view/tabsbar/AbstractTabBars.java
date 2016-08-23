package com.code.space.androidlib.view.tabsbar;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.code.space.androidlib.utilities.chain.params.LayoutParamsBuilder;
import com.code.space.androidlib.utilities.chain.params.ILayoutParamsBuilder;
import com.code.space.androidlib.utilities.utils.ViewUtils;

public abstract class AbstractTabBars extends LinearLayout implements View.OnClickListener{

    protected int lastCHeckedPosition = -1;

    protected View[] tabViews;

    private OnMenuTabClickListener listener;

    public AbstractTabBars(Context context) {
        super(context);
    }

    public AbstractTabBars(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @TargetApi(11)
    public AbstractTabBars(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(21)
    public AbstractTabBars(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public void setTabs(String... tabs){
        tabViews = new View[tabs.length];
        createTabs(tabs);
        setClick();
    }

    protected abstract void createTabs(String[] tabs);

    public void setClick(){
        for(View v:tabViews){
            v.setOnClickListener(this);
        }
    }

    public View getTabView(int position){
        return tabViews[position];
    }

    public void setTabView(int position, View view){
        tabViews[position] = view;
    }


    @Override
    public void onClick(View v) {
        TabViewHolder holder = (TabViewHolder) v.getTag();
        if(holder.position()== lastCHeckedPosition) return;
        setChecked(holder.position());
        if(listener!=null) listener.onTabClick(holder.position(),this);
    }

    public void setInitCheckState(int position){
        for(int i=0; i<tabViews.length; i++){
            if(i==position){
                setCheckedState(getTabView(i));
                lastCHeckedPosition = position;
            }
            else setUnCheckedState(getTabView(i));
        }
    }

    public void setChecked(int position){
        setCheckedState(getTabView(position));
        if(lastCHeckedPosition >=0) setUnCheckedState(getTabView(lastCHeckedPosition));
        lastCHeckedPosition = position;
    }

    protected abstract void setCheckedState(View tabView);
    protected abstract void setUnCheckedState(View tabView);


    public void setOnMenuTabClickListener(OnMenuTabClickListener l){
        this.listener = l;
    }

    public ILayoutParamsBuilder paramsBuilder(){
        return new ParamsBuilder();
    }

    public ILayoutParamsBuilder paramsBuilder(int width, int height){
        return new ParamsBuilder(width,height);
    }

    static class ParamsBuilder extends LayoutParamsBuilder<LayoutParams> {

        public ParamsBuilder() {
            params = new LinearLayout.LayoutParams(ViewUtils.WRAP,ViewUtils.WRAP);
        }

        public ParamsBuilder(int width, int height) {
            params = new LinearLayout.LayoutParams(width,height);
        }

        @Override
        public ParamsBuilder gravity(int gravity) {
            params.gravity = gravity;
            return this;
        }
    }
}
