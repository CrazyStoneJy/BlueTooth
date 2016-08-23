package com.code.space.androidlib.context.activity;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.util.Stack;

/**
 * a weak reference stack for storing activity
 */
public class ActivityStack {

    private static Stack<WeakReference<Activity>> activityStack;

    /**
     * put an activity into the stack
     * @param activity  the activity instance
     */
    public static void put(Activity activity){
        if(activityStack==null) activityStack = new Stack<WeakReference<Activity>>();
        activityStack.push(new WeakReference<Activity>(activity));
    }

    /**
     * remove the top activity from stack
     */
    public static Activity pop(){
        if(activityStack==null||activityStack.isEmpty()) return null;
        return activityStack.pop().get();
    }

    /**
     * get the top activity
     * @return null if no activity or the reference has failure
     */
    public static Activity topActivity(){
        if(activityStack==null||activityStack.isEmpty()) return null;
        return activityStack.peek().get();
    }

    /**
     * finish all activity in the stack
     */
    public static void finishAll(){
        if(activityStack==null||activityStack.isEmpty()) return;
        Activity a;
        while (!activityStack.isEmpty()){
            a = activityStack.pop().get();
            if(a!=null) a.finish();
        }
    }

    /**
     * flush the stack
     */
    public static void clean(){
        if(activityStack!=null){
            activityStack.clear();
            activityStack = null;
        }
    }
}
