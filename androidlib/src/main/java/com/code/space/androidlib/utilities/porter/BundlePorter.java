package com.code.space.androidlib.utilities.porter;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by shangxuebin on 2016/5/17.
 */
public abstract class BundlePorter implements DataPorter<Bundle>{

    @Override
    public Bundle writeToPorter() {
        return writeToPorter(new Bundle());
    }

    public Bundle writeToPorter(Fragment fragment) {
        Bundle b = writeToPorter();
        fragment.setArguments(b);
        return b;
    }
}
