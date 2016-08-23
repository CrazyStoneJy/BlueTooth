package com.code.space.androidlib.net.toolbox;

import com.code.space.androidlib.net.INetResult;
import com.code.space.androidlib.net.ResponseListener;
import com.code.space.androidlib.utilities.leak.LeakObject;

/**
 * Created by shangxuebin on 16-6-18.
 */
public class LeakResponseListener<RESPONSE> extends LeakObject<ResponseListener<RESPONSE>> implements ResponseListener<RESPONSE>{

    public LeakResponseListener(ResponseListener<RESPONSE> target) {
        super(target);
    }

    @Override
    public void onNetResult(INetResult<RESPONSE> result) {
        ResponseListener<RESPONSE> r = getTarget();
        if(r!=null) r.onNetResult(result);
    }
}
