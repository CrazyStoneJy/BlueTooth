package com.code.space.androidlib.utilities.porter;

/**
 * Created by shangxuebin on 16-5-16.
 */
public interface DataPorter<WRITE> {
    WRITE writeToPorter();
    WRITE writeToPorter(WRITE write);
    <D extends DataPorter>D readFromPorter();
}
