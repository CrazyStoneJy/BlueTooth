package com.google.zxing.activity;

import android.app.Activity;
import android.os.Handler;
import android.widget.Toast;

import com.example.bluetooth.utils.BLEUtils;

/**
 * Created by Administrator on 2016/8/23.
 */
public class CapturePresenter implements ICapturePresenter {

    private Activity mActivity;

    public CapturePresenter(Activity activity) {
        this.mActivity = activity;
    }


    @Override
    public void connectBlueTooth(String macAddress, Handler handler) {
        connectWithMac(macAddress,handler);
    }

    /**
     * 通过macaddress连接设备
     *
     * @param macAddress
     */
    private void connectWithMac(String macAddress,Handler handler) {
        Toast.makeText(mActivity, macAddress, Toast.LENGTH_SHORT).show();
//        BLEUtils.getInstance().openBlueTooth(mActivity);
        BLEUtils.getInstance().connect(BLEUtils.getInstance().getDeviceByMacAddress(macAddress));
    }
}
