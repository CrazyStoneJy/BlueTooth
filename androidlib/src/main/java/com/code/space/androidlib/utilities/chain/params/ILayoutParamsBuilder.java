package com.code.space.androidlib.utilities.chain.params;

import android.view.ViewGroup;

import com.code.space.androidlib.utilities.chain.IChainBuilder;

/**
 * Created by shangxuebin on 16-6-13.
 */
public interface ILayoutParamsBuilder extends IChainBuilder<ViewGroup.LayoutParams>{

        ILayoutParamsBuilder width(int width);

        ILayoutParamsBuilder height(int height);

        ILayoutParamsBuilder gravity(int gravity);

        ILayoutParamsBuilder margin(int left, int top, int right, int bottom);

        ILayoutParamsBuilder marginHorizontal(int margin);

        ILayoutParamsBuilder marginHorizontal(int left, int right);

        ILayoutParamsBuilder marginVertical(int margin);

        ILayoutParamsBuilder marginVertical(int top, int bottom);

        ILayoutParamsBuilder marginLeft(int left);

        ILayoutParamsBuilder marginTop(int top);

        ILayoutParamsBuilder marginRight(int right);

        ILayoutParamsBuilder marginBottom(int bottom);

}
