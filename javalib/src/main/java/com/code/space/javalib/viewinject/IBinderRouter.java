package com.code.space.javalib.viewinject;

/**
 * Created by shangxuebin on 16-5-18.
 */
public interface IBinderRouter {
    IViewFinder getViewFinder(Object target);
}
