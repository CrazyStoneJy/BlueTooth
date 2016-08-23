package com.code.space.androidlib.utilities.porter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * Created by shangxuebin on 2016/5/17.
 */
public abstract class IntentPorter implements DataPorter<Intent>{

    @Override
    public Intent writeToPorter() {
        return writeToPorter(new Intent());
    }

    public void startActivity(Context context, Class<? extends Activity> clazz){
        context.startActivity(writeToPorter(new Intent(context,clazz)));
    }

    public Intent writeToPorter(Context context, Class<? extends Activity> clazz) {
        return writeToPorter(new Intent(context,clazz));
    }
}
