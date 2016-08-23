package com.code.space.androidlib.context;

/**
 * Created by shangxuebin on 16-5-17.
 */
public interface LifeCircle {
    int CREATE = 1;
    int START = 2;
    int RESTART = 3;
    int RESUME = 4;
    int PAUSE = 5;
    int STOP = 6;
    int DESTROY = 7;
    int CREATE_VIEW = 8;
    int VIEW_CREATED = 9;
    int ATTACH = 10;
}
