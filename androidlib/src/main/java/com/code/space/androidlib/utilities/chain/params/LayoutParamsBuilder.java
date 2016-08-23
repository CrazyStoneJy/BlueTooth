package com.code.space.androidlib.utilities.chain.params;

import android.view.ViewGroup;

/**
 * Created by shangxuebin on 16-6-13.
 */
public class LayoutParamsBuilder<P extends ViewGroup.MarginLayoutParams> implements ILayoutParamsBuilder {

    protected P params;

    public LayoutParamsBuilder() {
    }

    public LayoutParamsBuilder(int width, int height){
    }

    public LayoutParamsBuilder(P params) {
        this.params = params;
    }

    protected P getParams(){
        return params;
    }

    @Override
    public LayoutParamsBuilder width(int width){
        getParams().width = width;
        return this;
    }

    @Override
    public LayoutParamsBuilder height(int height){
        getParams().height = height;
        return this;
    }

    public LayoutParamsBuilder gravity(int gravity){
        return this;
    }

    @Override
    public ILayoutParamsBuilder marginHorizontal(int margin) {
        marginHorizontal(margin,margin);
        return this;
    }

    @Override
    public ILayoutParamsBuilder marginHorizontal(int left, int right) {
        getParams().leftMargin = left;
        getParams().rightMargin = right;
        return this;
    }

    @Override
    public ILayoutParamsBuilder marginVertical(int margin) {
        marginVertical(margin,margin);
        return this;
    }

    @Override
    public ILayoutParamsBuilder marginVertical(int top, int bottom) {
        getParams().topMargin = top;
        getParams().bottomMargin = bottom;
        return this;
    }

    public LayoutParamsBuilder margin(int left, int top, int right, int bottom){
        getParams().setMargins(left,top,right,bottom);
        return this;
    }

    public LayoutParamsBuilder marginLeft(int left){
        getParams().leftMargin = left;
        return this;
    }

    public LayoutParamsBuilder marginTop(int top){
        getParams().topMargin = top;
        return this;
    }

    public LayoutParamsBuilder marginRight(int right){
        getParams().rightMargin = right;
        return this;
    }

    public LayoutParamsBuilder marginBottom(int bottom){
        getParams().bottomMargin = bottom;
        return this;
    }

    public ViewGroup.LayoutParams build(){
        return params;
    }
}
