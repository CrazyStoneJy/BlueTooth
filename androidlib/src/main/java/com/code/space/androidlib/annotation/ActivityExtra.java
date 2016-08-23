package com.code.space.androidlib.annotation;

import com.code.space.androidlib.context.activity.ActivityStack;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by shangxuebin on 16-5-16.
 * Annotation for marking an activity
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ActivityExtra {
    /**
     * automatically hide soft input
     * @return true if you hide soft input pad when touching outside an edit text
     */
    boolean hideKeyboard() default false;

    /**
     * store the activity instance in a weak reference
     * @return true if you want to store the activity in a static weak reference
     */
    boolean storeInstance()  default false;

    /**
     * put the activity instance into the Activity stack, you can use this to automatically put into and push from the stack
     * @return true if you want to do so. Call the {@link ActivityStack#finishAll()} to finish all activity instance stored in the stack
     * @see com.code.space.androidlib.context.activity.ActivityStack#activityStack
     */
    boolean addToStack() default false;
}
