package com.code.space.androidlib.utilities.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.ref.SoftReference;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shangxuebin on 2016/5/17.
 */
public class ViewUtils {

    public static final int MATCH = ViewGroup.LayoutParams.MATCH_PARENT;
    public static final int WRAP = ViewGroup.LayoutParams.WRAP_CONTENT;

    public static Resources getResources() {
        return getResources(null);
    }

    public static Resources getResources(Context context) {
        return ContextUtils.getApplication(context).getResources();
    }

    public static String getString(@StringRes int id, Context context) {
        return getResources(context).getString(id);
    }

    public static String getString(@StringRes int id, Fragment fragment) {
        return getString(id, fragment.getActivity());
    }

    public static String getString(@StringRes int id, View view) {
        return getString(id, view.getContext());
    }

    public static int getDimen(@DimenRes int id, Context context) {
        return getResources(context).getDimensionPixelSize(id);
    }

    public static int getDimen(@DimenRes int id, Fragment fragment) {
        return getDimen(id, fragment.getActivity());
    }

    public static int getDimen(@DimenRes int id, View view) {
        return getDimen(id, view.getContext());
    }

    public static int getColor(@ColorRes int id, Context context) {
        if (AppUtils.checkAPI(23)) return getResources(context).getColor(id, null);
        return getResources(context).getColor(id);
    }

    public static int getColor(@ColorRes int id, Fragment fragment) {
        return getColor(id, fragment.getActivity());
    }

    public static int getColor(@ColorRes int id, View view) {
        return getColor(id, view.getContext());
    }

    public static Drawable getDrawable(@DrawableRes int id, Context context) {
        if (AppUtils.checkAPI(21)) return getResources(context).getDrawable(id, null);
        return getResources(context).getDrawable(id);
    }

    public static Drawable getDrawable(@DrawableRes int id, Fragment fragment) {
        return getDrawable(id, fragment.getActivity());
    }

    public static Drawable getDrawable(@DrawableRes int id, View view) {
        return getDrawable(id, view.getContext());
    }


    /**
     * set the alpha of a view
     *
     * @param alpha the alpha, 0.0~1.0
     * @param v     the view to set alpha
     */
    public static void setAlpha(float alpha, View v) {
        if (Build.VERSION.SDK_INT < 11) {
            AlphaAnimation a1 = new AlphaAnimation(alpha, alpha);
            a1.setDuration(0);
            a1.setFillAfter(true);
            v.startAnimation(a1);
        } else {
            v.setAlpha(alpha);
        }
    }

    /**
     * create by crazystone
     * sp转px
     *
     * @param spValue sp值
     * @param context
     * @return px值
     */
    public static int sp2px(float spValue, Context context) {
        //final float scale = getResources(context).getDisplayMetrics().scaledDensity;
        //return (int) (spValue * scale + 0.5);
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, getResources(context).getDisplayMetrics());
    }

    /**
     * create by crazystone
     * px转sp
     *
     * @param pxValue px值
     * @param context
     * @return sp值
     */
    public static int px2sp(float pxValue, Context context) {
        final float scale = getResources(context).getDisplayMetrics().scaledDensity;
        return (int) (pxValue / scale + 0.5);
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */

    public static int dip2px(float dpValue) {
        return dip2px(dpValue, ContextUtils.getApplication(null));
    }

    public static int dip2px(float dpValue, Context context) {
        //final float scale = getResources(context).getDisplayMetrics().density;
        //return (int) (dpValue * scale + 0.5f);
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources(context).getDisplayMetrics());
    }

    public static int dip2px(float dpValue, Fragment fragment) {
        return dip2px(dpValue, fragment.getActivity());
    }

    public static int dip2px(float dpValue, View view) {
        return dip2px(dpValue, view.getContext());
    }


    /**
     * inflate a view from a xml file
     *
     * @param id       the xml layout id
     * @param rootView the ViewGroup to inflate the view
     * @param <VIEW>
     * @return view inflated
     */
    public static <VIEW extends View> VIEW inflate(@LayoutRes int id,
                                                   ViewGroup rootView) {
        return inflate(id, rootView, false);
    }

    /**
     * inflate a view from a xml file
     *
     * @param id           the xml layout id
     * @param rootView     the ViewGroup to attach
     * @param attachToRoot attach to the rootView or not
     * @param <VIEW>
     * @return view inflated
     */
    public static <VIEW extends View> VIEW inflate(@LayoutRes int id,
                                                   ViewGroup rootView, boolean attachToRoot) {
        return inflate(id, rootView.getContext(), rootView, attachToRoot);
    }

    /**
     * inflate a view from a xml file
     *
     * @param id      the xml layout id
     * @param context the context object to get the LayoutInflater
     * @param <VIEW>
     * @return view inflated
     */
    public static <VIEW extends View> VIEW inflate(@LayoutRes int id, Context context) {
        return inflate(id, context, null, false);
    }


    /**
     * @param id           the xml layout id
     * @param context      the context object to get the LayoutInflater
     * @param rootView     the ViewGroup to attach
     * @param attachToRoot attach to rootView or not
     * @param <VIEW>
     * @return view inflated
     * @see LayoutInflater#inflate(int, ViewGroup, boolean)
     * inflate a view from a xml file
     */
    @SuppressWarnings("unchecked")
    public static <VIEW extends View> VIEW inflate(@LayoutRes int id,
                                                   Context context, ViewGroup rootView, boolean attachToRoot) {
        return (VIEW) LayoutInflater.from(context).inflate(id, rootView,
                attachToRoot);
    }

    /**
     * the int array used to get the location of a view in window
     *
     * @see #isInView(float, float, View)
     */
    private static SoftReference<int[]> softLoc;

    /**
     * figure out if a point is in a view or not
     *
     * @param x    the x axis value of the point
     * @param y    the y axis value of the point
     * @param view the view object
     * @return true if in the view
     */
    public static boolean isInView(float x, float y, View view) {
        return isInView(x, y, view);
    }

    /**
     * a reload function to figure out if a point is in a view or not
     * this function will invoke {@link #isInView(float, float, View)}
     *
     * @param ev   the {@link MotionEvent} object
     * @param view the view object
     * @return true if in the view
     */
    public static boolean isInView(MotionEvent ev, View view) {
        return isInViewRange(ev, view, 0);
    }

    public static boolean isInViewRange(MotionEvent ev, View view, float offsite) {
        return isInViewRange(ev, view, offsite, offsite, offsite, offsite);
    }

    public static boolean isInViewRange(float x, float y, View view, float offsite) {
        return isInViewRange(x, y, view, offsite, offsite, offsite, offsite);
    }

    public static boolean isInViewRange(MotionEvent ev, View view, float left, float top, float right, float bottom) {
        return isInViewRange(ev.getX(), ev.getY(), view, left, top, right, bottom);
    }

    public static boolean isInViewRange(float x, float y, View view, float left, float top, float right, float bottom) {
        if (view == null)
            return false;
        if (softLoc == null || softLoc.get() == null)
            softLoc = new SoftReference<int[]>(new int[2]);
        int[] loc = softLoc.get();
        view.getLocationOnScreen(loc);
        return isInRange(x, y, loc[0] - left, loc[1] - top, loc[0] + view.getWidth() + right, loc[1] + view.getHeight() + top);
    }

    public static boolean isInRange(MotionEvent event, float left, float top, float right, float bottom) {
        return isInRange(event, left, top, right, bottom, 0);
    }

    public static boolean isInRange(MotionEvent event, float left, float top, float right, float bottom, float offset) {
        return isInRange(event.getX(), event.getY(), left, top, right, bottom, offset);
    }

    public static boolean isInRange(float x, float y, float left, float top, float right, float bottom) {
        return isInRange(x, y, left, top, right, bottom, 0);
    }

    public static boolean isInRange(float x, float y, float left, float top, float right, float bottom, float offset) {
        return x > (left - offset) && x < (right + offset) && y > (top - offset) && y < (bottom + offset);
    }

    public static void setTextDipSize(TextView tv, float dipSize) {
        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dipSize);
    }

    public static void setTextPxSize(TextView tv, int px) {
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, px);
    }

    public static void setTextDimenSize(TextView tv, @DimenRes int dimen) {
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, getDimen(dimen, tv.getContext()));
    }

    public static void setFont(TextView tv, Typeface typeface) {
        if (tv != null && typeface != null) tv.setTypeface(typeface);
        else if (AppUtils.debugging())
            throw new NullPointerException(StringUtils.splice("null ", tv == null ? "TextView" : "Typeface", " to set Typeface for a TextView"));
    }

    public static void setListener(View.OnClickListener l, View... views) {
        for (View v : views) {
            v.setOnClickListener(l);
        }
    }

    public static void setTextColor(TextView tv, @ColorRes int colorId) {
        tv.setTextColor(ViewUtils.getColor(colorId, tv));
    }

    public static DisplayMetrics getWindowMetrics(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager winManager = (WindowManager) ContextUtils.getApplication(context).getSystemService(Context.WINDOW_SERVICE);
        winManager.getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }
}
